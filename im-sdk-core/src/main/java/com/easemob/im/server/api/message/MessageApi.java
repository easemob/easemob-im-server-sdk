package com.easemob.im.server.api.message;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.message.deletechannel.DeleteMessageChannel;
import com.easemob.im.server.api.message.history.MessageHistory;
import com.easemob.im.server.api.message.missed.MessageMissed;
import com.easemob.im.server.api.message.missed.MissedMessageCount;
import com.easemob.im.server.api.message.recall.RecallMessage;
import com.easemob.im.server.api.message.recall.RecallMessageSource;
import com.easemob.im.server.api.message.send.SendMessage;
import com.easemob.im.server.api.message.status.MessageStatus;
import com.easemob.im.server.api.message.upload.ImportMessage;
import com.easemob.im.server.model.EMKeyValue;
import com.easemob.im.server.model.EMMessage;
import com.easemob.im.server.model.EMSentMessageIds;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * 消息API。
 */
public class MessageApi {

    private MessageMissed missed;

    private SendMessage sendMessage;

    private MessageHistory messageHistory;

    private MessageStatus messageStatus;

    private RecallMessage recallMessage;

    private DeleteMessageChannel deleteMessageChannel;

    private ImportMessage importMessage;

    public MessageApi(Context context) {
        EMProperties properties = context.getProperties();
        this.missed = new MessageMissed(context);
        this.sendMessage = new SendMessage(context);
        this.messageHistory = new MessageHistory(context, properties.getServerTimezone());
        this.messageStatus = new MessageStatus(context);
        this.recallMessage = new RecallMessage(context);
        this.deleteMessageChannel = new DeleteMessageChannel(context);
        this.importMessage = new ImportMessage(context);
    }

    /**
     * 查询用户离线消息数。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<MissedMessageCount> messages = service.message().countMissedMessages("username").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名
     * @return 每个对话的离线消息数
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E7%A6%BB%E7%BA%BF%E6%B6%88%E6%81%AF%E6%95%B0">获取用户离线消息数</a>
     */
    public Flux<MissedMessageCount> countMissedMessages(String username) {
        return this.missed.count(username);
    }

    /**
     * 查询某条离线消息的状态，如是否已经传达。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     Boolean isDelivered = service.message().isMessageDeliveredToUser("messageId", "toUser").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param messageId 要查看对消息ID
     * @param toUser    消息接收方的用户名
     * @return 是否送达或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E6%9F%90%E6%9D%A1%E7%A6%BB%E7%BA%BF%E6%B6%88%E6%81%AF%E7%8A%B6%E6%80%81">获取某条离线消息状态</a>
     */
    public Mono<Boolean> isMessageDeliveredToUser(String messageId, String toUser) {
        return this.messageStatus.isMessageDeliveredToUser(messageId, toUser);
    }

    /**
     * 构造消息并发送。
     * <p>
     * 例如，向用户发送一条带有扩展字段的文本消息:
     * <pre>{@code
     * EMService service;
     * try {
     *     service.message().send()
     *                      .fromUser("alice").toUser("rabbit")
     *                      .text(msg -> msg.text("hello"))
     *                      .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
     *                      .send()
     *                      .block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * <p>
     * 如果需要向一个群组或聊天室发消息，将示例中的 toUser 改成 toGroup 或 toRoom，并传入对应的群组或聊天室id
     * <p>
     * 如果需要向多个用户或群组或聊天室发消息，将示例中的 toUser 改成 toUsers 或 toGroups 或 toRooms，并传入对应的用户或群组或聊天室id
     * <p>
     * 将上述发送文本消息示例中的 `.text(...) `替换掉，来发送其他类型消息示例：
     * <p>
     * 发送图片消息：{@code .image(msg -> msg.uri(URI.create("http://example/image.png")).secret("secret").displayName("image.png"))}
     * <p>
     * 发送语音消息：{@code .voice(msg -> msg.uri(URI.create("http://example/voice.amr")).duration(3).secret("secret").displayName("voice.amr"))}
     * <p>
     * 发送视频消息：{@code .video(msg -> msg.uri(URI.create("http://example/video.mp4")).duration(3).secret("secret").displayName("video.mp4").thumb("http://example/videoThumbnail").thumbSecret("thumbSecret"))}
     * <p>
     * 发送文件消息：{@code .file(msg -> msg.uri(URI.create("http://example/file.txt")).secret("secret").displayName("file.txt"))}
     * <p>
     * 发送位置消息：{@code .location(msg -> msg.latitude(1.234567).longitude(1.234567).address("some where"))}
     * <p>
     * 发送自定义类型消息：{@code .custom(msg -> msg.customEvent("liked").customExtension("name", "forest"))}
     * <p>
     *
     * @return 发送消息的构造器
     * @see <a href="http://docs-im.easemob.com/im/server/basics/messages">发送消息</a>
     */
    public SendMessage send() {
        return this.sendMessage;
    }

    /**
     * 发送消息。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * 例如，向用户发送一条带有扩展字段的文本消息
     * Set<String> toUsers = new HashSet<>();
     * toUsers.add("toUserName");
     *
     * EMTextMessage textMessage = new EMTextMessage().text("hello");
     *
     * Set<EMKeyValue> exts = new HashSet<>();
     * exts.add(EMKeyValue.of("key", "value"));
     *
     * try {
     *     EMSentMessageIds messageIds = service.message().send("fromUserName", "users", toUsers, textMessage, exts).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * 例如，向群组发送一条带有扩展字段的图片消息
     * Set<String> toGroups = new HashSet<>();
     * toGroups.add("toGroupId");
     *
     * EMImageMessage imageMessage =
     *         new EMImageMessage().uri(URI.create("http://example/image.png")).secret("secret")
     *                 .displayName("image.png");
     *
     * Set<EMKeyValue> exts1 = new HashSet<>();
     * exts1.add(EMKeyValue.of("key", "value"));
     * exts1.add(EMKeyValue.of("key1", 10));
     * exts1.add(EMKeyValue.of("key2", new HashMap<String, String>() {
     *     {
     *         put("mkey1", "mvalue1");
     *         put("mkey2", "mvalue2");
     *     }
     * }));
     *
     * try {
     *     EMSentMessageIds messageIds = service.message().send("fromUserName", "chatgroups", toGroups, imageMessage, exts1).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * }</pre>
     *
     * @param from       发送者用户名
     * @param toType     目标类型，可以是 `users`, `chatgroups`, `chatrooms`
     * @param tos        目标id列表
     * @param message    要发送的消息，EMTextMessage文本消息，EMImageMessage图片消息，EMVoiceMessage语音消息，
     *                   EMVideoMessage视频消息，EMFileMessage文件消息，EMCommandMessage透传消息，EMCustomMessage自定义类型消息，
     *                   各种类型消息需要自己构造
     * @param extensions 要发送的扩展，可以为空
     * @return 发消息响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/messages">发送消息</a>
     */
    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions) {
        return this.sendMessage.send(from, toType, tos, message, extensions);
    }

    /**
     * 发送消息(只投递在线消息)。
     * <p>
     * API使用示例：请参考上方 发送消息
     * <p>
     *
     * @param from       发送者用户名
     * @param toType     目标类型，可以是 `users`, `chatgroups`, `chatrooms`
     * @param tos        目标id列表
     * @param message    要发送的消息
     * @param extensions 要发送的扩展，可以为空
     * @param routeType  只投递在线消息，请传入 `ROUTE_ONLINE`
     * @return 发消息响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/messages">发送消息</a>
     */
    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
                                       EMMessage message, Set<EMKeyValue> extensions, String routeType) {
        return this.sendMessage.send(from, toType, tos, message, extensions, routeType);
    }

    /**
     * 获取消息历史文件的下载地址。
     * <p>
     * 历史文件是每小时一个文件，比如指定12:10，则返回12点的历史文件。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     Instant now = Instant.parse("2021-05-20T18:00:00.631Z").minusMillis((TimeUnit.HOURS.toMillis(8)));
     *     String historyUrl = service.message().getHistoryAsUri(now).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param instant 时间点
     * @return 下载地址或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatrecord#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E6%B6%88%E6%81%AF%E6%96%87%E4%BB%B6">获取历史消息文件</a>
     */
    public Mono<String> getHistoryAsUri(Instant instant) {
        return this.messageHistory.toUri(instant);
    }

    /**
     * 下载消息历史文件到本地。
     * <p>
     * 消息历史文件是gz压缩的。
     * <p>
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * Instant now = Instant.parse("2021-05-20T18:00:00.631Z").minusMillis((TimeUnit.HOURS.toMillis(8)));
     * Path dir = Paths.get("/local/path/...");
     * try {
     *     Path path = service.message().getHistoryAsLocalFile(now, dir, "filename.gz").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param instant  时间点
     * @param dir      下载目录
     * @param filename 文件名，如果为空，则默认为YYYYMMDD.gz
     * @return 下载文件的路径或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatrecord#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E6%B6%88%E6%81%AF%E6%96%87%E4%BB%B6">获取历史消息文件</a>
     */
    public Mono<Path> getHistoryAsLocalFile(Instant instant, Path dir, String filename) {
        return this.messageHistory.toLocalFile(instant, dir, filename);
    }

    /**
     * 消息撤回。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<RecallMessageSource> messageSources = new ArrayList<>();
     *     messageSources.add(new RecallMessageSource("messageId", "chat", "u1", "u2", true));
     *     service.message().recallMessage(messageSources).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     * @param messageSources messageSources
     * @return 消息撤回响应或错误
     * @see <a href="https://docs-im.easemob.com/ccim/rest/message#%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%B6%88%E6%81%AF%E6%92%A4%E5%9B%9E">发送消息</a>
     */
    public Mono<Void> recallMessage(List<RecallMessageSource> messageSources) {
        return this.recallMessage.execute(messageSources);
    }

    /**
     * 服务端单向删除会话。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.message().deleteChannel("u1", "u2", "chat", false).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     * @param username username 删除会话方，例如 A 要将与 B 的单聊会话删除，username 为 A，channelName 为 B
     * @param channelName channelName 要删除的会话 ID
     * @param channelType channelType 会话类型。chat:单聊会话；groupchat:群聊会话
     * @param deleteRoam deleteRoam 是否删除服务端消息，不允许为空。true：是；false：否
     * @return 消息撤回响应或错误
     * @see <a href="https://docs-im.easemob.com/ccim/rest/message#%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%8D%95%E5%90%91%E5%88%A0%E9%99%A4%E4%BC%9A%E8%AF%9D">服务端单向删除会话</a>
     */
    public Mono<Void> deleteChannel(String username, String channelName, String channelType, Boolean deleteRoam) {
        return this.deleteMessageChannel.execute(username, channelName, channelType, deleteRoam);
    }

    /**
     * 导入单聊消息
     *
     * API使用示例：
     * <pre> {@code
     *
     * 例如，导入带扩展字段的文本消息
     *
     * EMTextMessage textMessage = new EMTextMessage().text("hello");
     *
     * Set<EMKeyValue> exts = new HashSet<>();
     * exts.add(EMKeyValue.of("key", "value"));
     *
     * try {
     *     Instant time = Instant.parse("2021-05-20T18:00:00.631Z").minusMillis((TimeUnit.HOURS.toMillis(8)));
     *     Long msgTimestamp = time.toEpochMilli();
     *     String messageId = service.message()
     *                               .importChatMessage("fromUserName", "toUserName",
     *                                  textMessage, exts, true, msgTimestamp, false)
     *                               .block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * }</pre>
     *
     * @param from       发送者用户名
     * @param to         目标用户名
     * @param message    要导入的消息，EMTextMessage文本消息，EMImageMessage图片消息，EMVoiceMessage语音消息，
     *                   EMVideoMessage视频消息，EMFileMessage文件消息，EMCommandMessage透传消息，EMCustomMessage自定义类型消息，
     *                   各种类型消息需要自己构造
     * @param extensions 要导入的扩展内容，可以为空
     * @param isAckRead  是否设置消息为已读
     * @param msgTimestamp 导入的消息的时间戳
     * @param needDownload 是否需要下载附件资源
     * @return 导入成功后消息的id
     */
    public Mono<String> importChatMessage(String from, String to,
            EMMessage message, Set<EMKeyValue> extensions, Boolean isAckRead,
            Long msgTimestamp, Boolean needDownload) {
        return this.importMessage.importChatMessage(from, to, message, extensions,
                isAckRead, msgTimestamp, needDownload);
    }

    /**
     * 导入群聊消息
     *
     * API使用示例：
     * <pre> {@code
     *
     * 例如，导入带扩展字段的文本消息
     *
     * EMTextMessage textMessage = new EMTextMessage().text("hello");
     *
     * Set<EMKeyValue> exts = new HashSet<>();
     * exts.add(EMKeyValue.of("key", "value"));
     *
     * try {
     *     Instant time = Instant.parse("2021-05-20T18:00:00.631Z").minusMillis((TimeUnit.HOURS.toMillis(8)));
     *     Long msgTimestamp = time.toEpochMilli();
     *     String groupId = "18273849454"
     *     String messageId = service.message()
     *                               .importChatGroupMessage("fromUserName", groupId,
     *                                  textMessage, exts, true, msgTimestamp, false)
     *                               .block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param from       发送者用户名
     * @param to         目标群id
     * @param message    要导入的消息，EMTextMessage文本消息，EMImageMessage图片消息，EMVoiceMessage语音消息，
     *                   EMVideoMessage视频消息，EMFileMessage文件消息，EMCommandMessage透传消息，EMCustomMessage自定义类型消息，
     *                   各种类型消息需要自己构造
     * @param extensions 要导入的扩展内容，可以为空
     * @param isAckRead  是否设置消息为已读
     * @param msgTimestamp 导入的消息的时间戳
     * @param needDownload 是否需要下载附件资源
     * @return 导入成功后消息的id
     */
    public Mono<String> importChatGroupMessage(String from, String to,
            EMMessage message, Set<EMKeyValue> extensions, Boolean isAckRead,
            Long msgTimestamp, Boolean needDownload) {
        return this.importMessage.importChatGroupMessage(from, to, message, extensions, isAckRead,
                msgTimestamp, needDownload);
    }
}
