package com.easemob.im.server.api.message;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.message.exception.MessageException;
import com.easemob.im.server.model.Message;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class MessageApi {

    private static final Pattern VALID_USERNAME_PATTERN = Pattern.compile("[A-Za-z-0-9]{1,64}");

    private static final Pattern VALID_CUSTOM_EVENT_PATTERN = Pattern.compile("[a-zA-Z0-9-_/.]{1,32}");

    private final HttpClient http;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    private final EMProperties properties;

    private final Cache<String, String> tokenCache;

    public MessageApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator, EMProperties properties, Cache<String, String> tokenCache) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
        this.properties = properties;
        this.tokenCache = tokenCache;
    }

    /**
     * 发送消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E6%B6%88%E6%81%AF
     *
     * 给一个或者多个用户，或者一个或者多个群组发送消息，并且通过可选的 from 字段让接收方看到发送方是不同的人
     * 同时，支持扩展字段，通过 ext 属性，APP 可以发送自己专属的消息结构
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试，同时用户消息+扩展字段的长度在4k字节以内
     *
     * @param targetType      发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target          发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，
     *                        也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param messageContent  消息内容
     * @param from            表示消息发送者
     * @param ext             扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendTextMessage(TargetType targetType, Set<String> target, String messageContent, String from, Map<String, Object> ext) {
        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "txt");
        msg.put("msg", messageContent);

        return buildMessage(targetType, target, msg, from, ext);
    }

    /**
     * 发送图片消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E5%9B%BE%E7%89%87%E6%B6%88%E6%81%AF
     *
     * 发送图片文件，需要先上传图片文件，然后再发送此消息。（URL 中的 UUID 和 secret 可以从上传后的 response 获取）
     * 上传附件接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD
     * 图片的大小不要超过10MB，额外开通限制服务的除外
     *
     * @param targetType     发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target         发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param imageUrl       域名/orgname/appname/chatfiles/成功上传文件返回的UUID。参考请求示例
     * @param imageFileName  图片名称
     * @param secret         成功上传图片文件后返回的secret
     * @param imageWidth     图片宽度，是可选的，不需要可以不传
     * @param imageHeight    图片高度，是可选的，不需要可以不传
     * @param from           表示消息发送者
     * @param ext            扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendImageMessage(TargetType targetType, Set<String> target, String imageUrl, String imageFileName, String secret, Long imageWidth, Long imageHeight,String from, Map<String, Object> ext) {
        verifyUrl(imageUrl);
        verifySecret(secret);

        ObjectNode imageSize = this.mapper.createObjectNode();
        if (imageWidth == null) {
            imageSize.put("width", 0);
        } else {
            imageSize.put("width", imageWidth);
        }

        if (imageHeight == null) {
            imageSize.put("height", 0);
        } else {
            imageSize.put("height", imageHeight);
        }

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "img");
        msg.put("url", imageUrl);
        msg.put("filename", imageFileName);
        msg.put("secret", secret);
        if (imageWidth != null || imageHeight != null) {
            msg.set("size", imageSize);
        }
        return buildMessage(targetType, target, msg, from, ext);
    }

    /**
     * 发送语音消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E8%AF%AD%E9%9F%B3%E6%B6%88%E6%81%AF
     *
     * 发送语音文件，需要先上传语音文件，然后再发送此消息。（URL 中的 UUID 和 secret 可以从上传后的 response 获取）
     * 上传附件接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD
     * 语音的大小不要超过10MB，额外开通限制服务的除外
     *
     * @param targetType     发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target         发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param audioUrl       域名/orgname/appname/chatfiles/成功上传文件返回的UUID。参考请求示例
     * @param audioFileName  语音名称
     * @param secret         成功上传语音文件后返回的secret
     * @param audioLength    语音时间（单位：秒）
     * @param from           表示消息发送者
     * @param ext            扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendAudioMessage(TargetType targetType, Set<String> target, String audioUrl, String audioFileName, String secret, Integer audioLength,String from, Map<String, Object> ext) {
        verifyUrl(audioUrl);
        verifySecret(secret);

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "audio");
        msg.put("url", audioUrl);
        msg.put("filename", audioFileName);
        msg.put("secret", secret);
        msg.put("length", audioLength);

        return buildMessage(targetType, target, msg, from, ext);
    }

    /**
     * 发送视频消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E8%A7%86%E9%A2%91%E6%B6%88%E6%81%AF
     *
     * 发送视频消息，需要先上传视频文件和视频缩略图文件，然后再发送此消息。（URL 中的 UUID 和 secret 可以从上传后的 response 获取）
     * 上传附件接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD
     * 视频的大小不要超过10MB，额外开通限制服务的除外
     *
     * @param targetType           发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target               发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param videoUrl             域名/orgname/appname/chatfiles/成功上传文件返回的UUID。参考请求示例
     * @param videoFileName        视频文件名称
     * @param secret               成功上传视频文件后返回的secret
     * @param videoPlaybackLength  视频播放长度
     * @param videoFileLength      视频文件大小（单位：字节）
     * @param thumbUrl             成功上传视频缩略图返回的UUID，域名/orgname/appname/chatfiles/成功上传文件返回的UUID
     * @param thumbSecret          成功上传视频缩略图后返回的secret
     * @param from                 表示消息发送者
     * @param ext                  扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendVideoMessage(TargetType targetType, Set<String> target, String videoUrl, String videoFileName, String secret, Integer videoPlaybackLength, Long videoFileLength, String thumbUrl, String thumbSecret, String from, Map<String, Object> ext) {
        verifyUrl(videoUrl);
        verifySecret(secret);
        verifyThumbUrl(thumbUrl);
        verifyThumbSecret(thumbSecret);

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "video");
        msg.put("url", videoUrl);
        msg.put("filename", videoFileName);
        msg.put("thumb", thumbUrl);
        msg.put("length", videoPlaybackLength);
        msg.put("file_length", videoFileLength);
        msg.put("thumb_secret", thumbSecret);

        return buildMessage(targetType, target, msg, from, ext);
    }

    /**
     * 发送位置消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E4%BD%8D%E7%BD%AE%E6%B6%88%E6%81%AF
     *
     * 位置消息：获取到地址的经纬度，填写正确地址发送
     *
     * @param targetType  发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target      发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param longitude   经度
     * @param latitude    纬度
     * @param address     详细地址
     * @param from        表示消息发送者
     * @param ext         扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendLocationMessage(TargetType targetType, Set<String> target, String longitude, String latitude, String address, String from, Map<String, Object> ext) {
        verifyLongitude(longitude);
        verifyLatitude(latitude);

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "loc");
        msg.put("lng", longitude);
        msg.put("lat", latitude);
        msg.put("addr", address);

        return buildMessage(targetType, target, msg, from, ext);
    }

    /**
     * 发送透传消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E9%80%8F%E4%BC%A0%E6%B6%88%E6%81%AF
     *
     * 透传消息：不会在客户端提示（铃声、震动、通知栏等），也不会有 APNS 推送（苹果推送），但可以在客户端监听到，具体功能可以根据自身自定义。
     *
     * @param targetType  发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target      发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param action      由APP自己定义action字段
     * @param from        表示消息发送者
     * @param ext         扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendCmdMessage(TargetType targetType, Set<String> target, String action, String from, Map<String, Object> ext) {
        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "cmd");
        msg.put("action", action);

        return buildMessage(targetType, target, msg, from, ext);
    }

    /**
     * 发送自定义消息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/messages#%E5%8F%91%E9%80%81%E8%87%AA%E5%AE%9A%E4%B9%89%E6%B6%88%E6%81%AF
     *
     * 若普通消息类型不满足用户消息需求，可以使用自定义消息来自定义消息类型，主要是通过message的customEvent字段实现。
     *
     * @param targetType   发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @param target       发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param customEvent  用户自定义的事件类型，必须是string，值必须满足正则表达式 [a-zA-Z0-9-_/\.]{1,32}，最短1个字符 最长32个字符
     * @param customExts   用户自定义的事件属性，类型必须是Map，最多可以包含16个元素。customExts 是可选的，不需要可以不传
     * @param from         表示消息发送者
     * @param ext          扩展属性，由APP自己定义
     * @return Message
     */
    public Message sendCustomMessage(TargetType targetType, Set<String> target, String customEvent, Map<String, Object> customExts, String from, Map<String, Object> ext) {
        verifyCustomEvent(customEvent);

        ObjectNode customExtJsonNode;
        try {
            String json = this.mapper.writeValueAsString(customExts);
            customExtJsonNode = mapper.readValue(json, ObjectNode.class);
        } catch (JsonProcessingException e) {
            throw new MessageException("message to json " + e);
        }

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("type", "custom");
        msg.put("customEvent", customEvent);
        if (customExts != null) {
            if (customExts.size() > 16) {
                throw new MessageException("Bad Request invalid customExts");
            }
            msg.set("customExts", customExtJsonNode);
        }

        return buildMessage(targetType, target, msg, from, ext);
    }

    public Message buildMessage(TargetType targetType, Set<String> target, ObjectNode msg, String from, Map<String, Object> ext) {
        verifyUsername(from);
        if (target == null || target.size() < 1 | target.size() > 1000) {
            throw new MessageException("Bad Request invalid targets");
        }

        for (String targetUsername : target) {
            verifyTargetUsername(targetUsername);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("target_type", targetType.toString());
        request.set("target", mapper.valueToTree(target));
        request.set("msg", msg);
        request.put("from", from);
        if (ext != null) {
            ObjectNode extJsonNode;
            try {
                String json = this.mapper.writeValueAsString(ext);
                extJsonNode = mapper.readValue(json, ObjectNode.class);
            } catch (JsonProcessingException e) {
                throw new MessageException("message to json " + e);
            }
            request.set("ext", extJsonNode);
        }

        JsonNode result = HttpUtils.execute(this.http, HttpMethod.POST, "/messages", request, this.allocator, this.mapper, this.properties, this.tokenCache);
        JsonNode data;
        if(result != null) {
            if (result.get("data") != null) {
                data = result.get("data");
            } else {
                throw new MessageException("data is null");
            }
        } else {
            throw new MessageException("response is null");
        }

        Map<String, String> targetMap;
        try {
            targetMap = mapper.treeToValue(data, Map.class);
        } catch (JsonProcessingException e) {
            throw new MessageException("json target to map fail " + e);
        }

        Map<String, Object> msgContextMap;
        try {
            msgContextMap = mapper.treeToValue(msg, Map.class);
        } catch (JsonProcessingException e) {
            throw new MessageException("json msg to map fail " + e);
        }

        return Message.builder()
                .targetType(targetType)
                .from(from)
                .target(targetMap)
                .messageContext(msgContextMap)
                .sendMessageTimestamp(result.get("timestamp").asLong())
                .ext(ext)
                .build();
    }

    // 验证 username
    private void verifyUsername(String username) {
        if (username == null || !VALID_USERNAME_PATTERN.matcher(username).matches()) {
            throw new MessageException(String.format("Bad Request %s invalid username", username));
        }
    }

    // 验证发送的目标
    private void verifyTargetUsername(String targetUsername) {
        if (targetUsername == null || !VALID_USERNAME_PATTERN.matcher(targetUsername).matches()) {
            throw new MessageException(String.format("Bad Request %s invalid target username", targetUsername));
        }
    }

    // 验证附件类型消息的url
    private void verifyUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new MessageException("Bad Request invalid url");
        }
    }

    // 验证附件类型消息的secret
    private void verifySecret(String secret) {
        if (secret == null || secret.isEmpty()) {
            throw new MessageException("Bad Request invalid secret");
        }
    }

    // 验证视频消息的thumbUrl
    private void verifyThumbUrl(String thumbUrl) {
        if (thumbUrl == null || thumbUrl.isEmpty()) {
            throw new MessageException("Bad Request invalid thumbUrl");
        }
    }

    // 验证视频消息的thumbSecret
    private void verifyThumbSecret(String thumbSecret) {
        if (thumbSecret == null || thumbSecret.isEmpty()) {
            throw new MessageException("Bad Request invalid thumbSecret");
        }
    }

    // 验证位置消息的lng
    private void verifyLongitude(String longitude) {
        if (longitude == null || longitude.isEmpty()) {
            throw new MessageException("Bad Request invalid longitude");
        }
    }

    // 验证位置消息的lat
    private void verifyLatitude(String latitude) {
        if (latitude == null || latitude.isEmpty()) {
            throw new MessageException("Bad Request invalid latitude");
        }
    }

    // 验证自定义类型消息的CustomEvent
    private void verifyCustomEvent(String customEvent) {
        if (customEvent == null || !VALID_CUSTOM_EVENT_PATTERN.matcher(customEvent).matches()) {
            throw new MessageException(String.format("Bad Request %s invalid customEvent", customEvent));
        }
    }

}
