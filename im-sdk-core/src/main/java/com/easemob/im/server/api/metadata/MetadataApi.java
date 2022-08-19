package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.chatroom.AutoDelete;
import com.easemob.im.server.api.metadata.chatroom.delete.ChatRoomMetadataDelete;
import com.easemob.im.server.api.metadata.chatroom.delete.ChatRoomMetadataDeleteResponse;
import com.easemob.im.server.api.metadata.chatroom.get.ChatRoomMetadataGet;
import com.easemob.im.server.api.metadata.chatroom.get.ChatRoomMetadataGetResponse;
import com.easemob.im.server.api.metadata.chatroom.set.ChatRoomMetadataSet;
import com.easemob.im.server.api.metadata.chatroom.set.ChatRoomMetadataSetResponse;
import com.easemob.im.server.api.metadata.user.delete.MetadataDelete;
import com.easemob.im.server.api.metadata.user.get.MetadataGet;
import com.easemob.im.server.api.metadata.user.set.MetadataSet;
import com.easemob.im.server.api.metadata.user.usage.MetadataUsage;
import com.easemob.im.server.model.EMMetadata;
import com.easemob.im.server.model.EMMetadataUsage;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 用户、聊天室属性API。
 */
public class MetadataApi {

    private MetadataSet metadataSet;

    private MetadataGet metadataGet;

    private MetadataUsage metadataUsage;

    private MetadataDelete metadataDelete;

    private ChatRoomMetadataSet chatRoomMetadataSet;

    private ChatRoomMetadataGet chatRoomMetadataGet;

    private ChatRoomMetadataDelete chatRoomMetadataDelete;

    public MetadataApi(Context context) {
        this.metadataSet = new MetadataSet(context);
        this.metadataGet = new MetadataGet(context);
        this.metadataUsage = new MetadataUsage(context);
        this.metadataDelete = new MetadataDelete(context);
        this.chatRoomMetadataSet=new ChatRoomMetadataSet(context);
        this.chatRoomMetadataGet=new ChatRoomMetadataGet(context);
        this.chatRoomMetadataDelete=new ChatRoomMetadataDelete(context);
    }

    /**
     * 设置用户属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * Map<String, String> map = new HashMap<>();
     * map.put("nickname", "昵称");
     * map.put("avatarurl", "http://www.easemob.com/avatar.png");
     * map.put("phone", "159");
     *
     * try {
     *     service.metadata().setMetadataToUser("username", map).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要被设置用户属性的用户名
     * @param metadata 要设置的属性
     * @return 成功或错误
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">设置用户属性</a>
     */
    public Mono<Void> setMetadataToUser(String username, Map<String, String> metadata) {
        return this.metadataSet.toUser(username, metadata);
    }

    /**
     * 获取用户属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     EMMetadata userMetadata = service.metadata().getMetadataFromUser("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要获取的用户名
     * @return 返回获取到的用户属性
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">获取用户属性</a>
     */
    public Mono<EMMetadata> getMetadataFromUser(String username) {
        return this.metadataGet.fromUser(username);
    }

    /**
     * 获取app用户属性当前所占空间。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     EMMetadataUsage metadataUsage = service.metadata().getUsage().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 返回占用空间大小，单位Bytes
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7%E6%80%BB%E9%87%8F%E5%A4%A7%E5%B0%8F">获取app用户属性总量大小</a>
     */
    public Mono<EMMetadataUsage> getUsage() {
        return this.metadataUsage.getUsage();
    }

    /**
     * 删除用户属性。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     Boolean isSuc = service.metadata().deleteMetadataFromUser("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要被删除用户属性的用户名
     * @return 返回删除是否成功的结果
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">删除用户属性</a>
     */
    public Mono<Boolean> deleteMetadataFromUser(String username) {
        return this.metadataDelete.fromUser(username);
    }

    /**
     * 设置聊天室属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * Map<String, String> map = new HashMap<>();
     * map.put("nickname", "昵称");
     * map.put("avatarurl", "http://www.easemob.com/avatar.png");
     * map.put("phone", "159");
     *
     * try {
     *     service.metadata().setChatRoomMetadata("username", "roomId", map, AutoDelete.NO_DELETE).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param operator   设置聊天室属性的操作人
     * @param chatroomId 聊天室id
     * @param metadata   要设置的属性
     * @param autoDelete 用户退出是否删除属性
     * @return 成功或错误
     */
    public Mono<ChatRoomMetadataSetResponse> setChatRoomMetadata(String operator, String chatroomId,
            Map<String, String> metadata,
            AutoDelete autoDelete) {
        return this.chatRoomMetadataSet.toChatRoom(operator, chatroomId, metadata, autoDelete);
    }

    /**
     * 强制设置聊天室属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * Map<String, String> map = new HashMap<>();
     * map.put("nickname", "昵称");
     * map.put("avatarurl", "http://www.easemob.com/avatar.png");
     * map.put("phone", "159");
     *
     * try {
     *     service.metadata().setChatRoomMetadataToUserForced("username", "roomId", map, AutoDelete.NO_DELETE).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId 聊天室id
     * @param operator      强制设置聊天室属性的操作人
     * @param metadata   要设强制设置的属性
     * @param autoDelete 用户退出是否删除属性
     * @return 成功或错误
     */
    public Mono<ChatRoomMetadataSetResponse> setChatRoomMetadataForced(String operator,
            String chatroomId, Map<String, String> metadata,
            AutoDelete autoDelete) {
        return this.chatRoomMetadataSet.toChatRoomForced(operator, chatroomId, metadata, autoDelete);
    }

    /**
     * 获取聊天室属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> keys = new ArrayList<>();
     * keys.add("nickname");
     *
     * try {
     *     service.metadata().listChatRoomMetadata("roomId", keys).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId 聊天室id
     * @param keys       要获取的属性key列表
     * @return 成功或错误
     */
    public Mono<ChatRoomMetadataGetResponse> listChatRoomMetadata(String chatroomId,
            List<String> keys) {
        return this.chatRoomMetadataGet.listChatRoomMetadata(chatroomId, keys);
    }

    /**
     * 获取所有的聊天室属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     service.metadata().listChatRoomMetadataAll("roomId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId 聊天室id
     * @return 成功或错误
     */
    public Mono<ChatRoomMetadataGetResponse> listChatRoomMetadataAll(String chatroomId) {
        return this.chatRoomMetadataGet.listChatRoomMetadataAll(chatroomId);
    }

    /**
     * 删除聊天室属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> keys = new ArrayList<>();
     * keys.add("nickname");
     *
     * try {
     *     service.metadata().deleteChatRoomMetadata("operator", "roomId", map, keys).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param operator   执行删除的操作人
     * @param chatroomId 聊天室id
     * @param keys       要删除的属性key列表
     * @return 成功或错误
     */
    public Mono<ChatRoomMetadataDeleteResponse> deleteChatRoomMetadata(String operator,
            String chatroomId, List<String> keys) {
        return this.chatRoomMetadataDelete.fromChatRoom(operator, chatroomId, keys);
    }

    /**
     * 强制删除聊天室属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> keys = new ArrayList<>();
     * keys.add("nickname");
     *
     * try {
     *     service.metadata().deleteChatRoomMetadataForced("roomId", map, keys).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId 聊天室id
     * @param keys       要强制删除的属性key列表
     * @return 成功或错误
     */
    public Mono<ChatRoomMetadataDeleteResponse> deleteChatRoomMetadataForced(String chatroomId,
            List<String> keys) {
        return this.chatRoomMetadataDelete.fromChatRoomForced(chatroomId, keys);
    }

}
