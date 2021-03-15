package com.easemob.im.server.api.chatrooms;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.ApiException;
import com.easemob.im.server.api.chatrooms.exception.ChatRoomsException;
import com.easemob.im.server.model.ChatRoom;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

import java.util.Set;
import java.util.regex.Pattern;

public class ChatRoomsApi {
    private static final Pattern VALID_CHAT_ROOM_USERNAME_PATTERN = Pattern.compile("[A-Za-z-0-9]{1,64}");

    private static final Pattern VALID_CHAT_ROOM_ID_PATTERN = Pattern.compile("[1-9][0-9]+");

    private final HttpClient http;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    private final EMProperties properties;

    private final Cache<String, String> tokenCache;

    public ChatRoomsApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator, EMProperties properties, Cache<String, String> tokenCache) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
        this.properties = properties;
        this.tokenCache = tokenCache;
    }

    /**
     * 分页获取聊天室成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98
     *
     * 可以分页获取聊天室成员列表的接口
     *
     * @param roomId   需要获取的聊天室 ID
     * @param pageNum   要获取第几页
     * @param pageSize  每页获取多少条
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom getChatRoomMembers(String roomId, Integer pageNum, Integer pageSize) throws ChatRoomsException {
        verifyRoomId(roomId);
        if (pageNum == null || pageNum < 0) {
            throw new ChatRoomsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize < 0) {
            throw new ChatRoomsException("Bad Request invalid pageSize");
        }

        String uri = "/chatrooms/" + roomId + "/users?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加单个聊天室成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98
     *
     * 一次给聊天室添加一个成员，不同重复添加同一个成员。如果用户已经是聊天室成员，将添加失败，并返回错误
     *
     * @param roomId   需要添加的聊天室成员的聊天室 ID
     * @param username  需要添加的 IM 用户名
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom addChatRoomMember(String roomId, String username) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsername(username);

        String uri = "/chatrooms/" + roomId + "/users/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 批量添加聊天室成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%89%B9%E9%87%8F%E6%B7%BB%E5%8A%A0%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98
     *
     * 向聊天室添加多位用户，一次性最多可添加60位用户
     *
     * @param roomId    需要添加的聊天室 ID
     * @param usernames  需要添加到聊天室的用户ID列表
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom batchAddChatRoomMember(String roomId, Set<String> usernames) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsernames(usernames);
        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/chatrooms/" + roomId + "/users";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 删除单个聊天室成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%A0%E9%99%A4%E5%8D%95%E4%B8%AA%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98
     *
     * 从聊天室删除一个成员。如果被删除用户不在聊天室中，或者聊天室不存在，将返回错误
     *
     * @param roomId   需要移除用户的聊天室 ID
     * @param username  需要移除的 IM 用户名
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom deleteChatRoomMember(String roomId, String username) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsername(username);

        String uri = "/chatrooms/" + roomId + "/users/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 批量移除聊天室成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%89%B9%E9%87%8F%E5%88%A0%E9%99%A4%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98
     *
     * @param roomId   需要移除用户的聊天室 ID
     * @param members  需要移除的 IM 用户列表
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom batchDeleteChatRoomMember(String roomId, Set<String> members) throws ChatRoomsException {
        verifyRoomId(roomId);

        String uri = "/chatrooms/" + roomId + "/users/" + verifySplitUsernames(members);
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 获取聊天室管理员列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8
     *
     * @param roomId  需要获取的聊天室 ID
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom getChatRoomAdminList(String roomId) throws ChatRoomsException {
        verifyRoomId(roomId);

        String uri = "/chatrooms/" + roomId + "/admin";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加聊天室管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 将一个聊天室成员角色提升为聊天室管理员
     *
     * @param roomId   需要添加管理员的聊天室 ID
     * @param newAdmin  需要添加为管理员的用户 ID
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom addChatRoomAdmin(String roomId, String newAdmin) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsername(newAdmin);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newadmin", newAdmin);

        String uri = "/chatrooms/" + roomId + "/admin";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 移除聊天室管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 将用户的角色从聊天室管理员降为普通聊天室成员
     *
     * @param roomId   需要移除的管理员所在聊天室 ID
     * @param oldAdmin  需要移除的管理员的用户 ID
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom removeChatRoomAdmin(String roomId, String oldAdmin) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsername(oldAdmin);

        String uri = "/chatrooms/" + roomId + "/admin/" + oldAdmin;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加单个用户禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E7%A6%81%E8%A8%80
     *
     * 将一个用户禁言。用户被禁言后，将无法在聊天室中发送消息
     *
     * @param roomId        需要添加禁言的聊天室 ID
     * @param username      要被禁言的 IM 用户名
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom addMute(String roomId, String username, Long muteDuration) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.createArrayNode().addPOJO(username));
        request.put("mute_duration", muteDuration);

        String uri = "/chatrooms/" + roomId + "/mute";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加多个用户禁言
     *
     * @param roomId    需要添加禁言的聊天室 ID
     * @param usernames  要被禁言的 IM 用户列表
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom addMute(String roomId, Set<String> usernames, Long muteDuration) throws ChatRoomsException {
        verifyRoomId(roomId);
        if (usernames == null || usernames.size() < 1) {
            throw new ChatRoomsException("Bad Request invalid usernames");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));
        request.put("mute_duration", muteDuration);

        String uri = "/chatrooms/" + roomId + "/mute";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 移除单个用户禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E7%A6%81%E8%A8%80
     *
     * 将用户从禁言列表中移除。移除后，用户可以正常在聊天室中发送消息
     *
     * @param roomId   需要移除禁言的聊天室 ID
     * @param member   需要移除禁言的用户 ID
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom removeMute(String roomId, String member) throws ChatRoomsException {
        verifyRoomId(roomId);
        verifyUsername(member);

        String uri = "/chatrooms/" + roomId + "/mute/" + member;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 移除多个用户禁言
     *
     * @param roomId   需要移除禁言的聊天室 ID
     * @param members  需要移除禁言的用户 ID列表
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom removeMute(String roomId, Set<String> members) throws ChatRoomsException {
        verifyRoomId(roomId);
        if (members == null || members.size() < 1) {
            throw new ChatRoomsException("Bad Request invalid members");
        }

        StringBuilder splitMember = new StringBuilder();
        for (String member : members) {
            verifyUsername(member);
            splitMember.append(member).append(",");
        }

        String uri = "/chatrooms/" + roomId + "/mute/" + splitMember.substring(0, splitMember.length() - 1);
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 获取禁言列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E7%A6%81%E8%A8%80%E5%88%97%E8%A1%A8
     *
     * @param roomId  需要添加禁言列表的聊天室 ID
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom getMuteList(String roomId) throws ChatRoomsException {
        verifyRoomId(roomId);

        String uri = "/chatrooms/" + roomId + "/mute";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 分页获取聊天室超级管理员列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8
     *
     * @param pageNum   要获取第几页
     * @param pageSize  每页获取多少条
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom getChatRoomSuperAdminList(Integer pageNum, Integer pageSize) throws ChatRoomsException {
        if (pageNum == null || pageNum < 0) {
            throw new ChatRoomsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize < 0) {
            throw new ChatRoomsException("Bad Request invalid pageSize");
        }

        String uri = "/chatrooms/super_admin?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(null, response);
    }

    /**
     * 添加超级管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 给用户添加聊天室超级管理员身份
     *
     * @param username  添加为超级管理员的 IM 用户名
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom addChatRoomSuperAdmin(String username) throws ChatRoomsException {
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("superadmin", username);

        String uri = "/chatrooms/super_admin";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(null, response);
    }

    /**
     * 移除超级管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98
     *
     * @param username  需要移除的 IM 用户名
     * @return ChatRoom
     * @throws ChatRoomsException 调用聊天室方法会抛出的异常
     */
    public ChatRoom removeChatRoomSuperAdmin(String username) throws ChatRoomsException {
        verifyUsername(username);

        String uri = "/chatrooms/super_admin/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatRoomsException(e.getMessage());
        }
        return responseToChatRoomObject(null, response);
    }

    // 验证 username
    private void verifyUsername(String username) throws ChatRoomsException {
        if (username == null || !VALID_CHAT_ROOM_USERNAME_PATTERN.matcher(username).matches()) {
            throw new ChatRoomsException(String.format("Bad Request %s invalid username", username));
        }
    }

    // 验证 roomId
    private void verifyRoomId(String roomId) throws ChatRoomsException {
        if (roomId == null || !VALID_CHAT_ROOM_ID_PATTERN.matcher(roomId).matches()) {
            throw new ChatRoomsException("Bad Request invalid roomId");
        }
    }

    // 验证 usernames
    private void verifyUsernames(Set<String> usernames) throws ChatRoomsException {
        if (usernames == null || usernames.size() < 1 || usernames.size() > 60) {
            throw new ChatRoomsException("Bad Request invalid usernames");
        }
    }

    // 验证 split usernames
    private String verifySplitUsernames(Set<String> usernames) {
        verifyUsernames(usernames);

        StringBuilder splitUsername = new StringBuilder();
        for (String username : usernames) {
            verifyUsername(username);
            splitUsername.append(username).append(",");
        }
        return splitUsername.substring(0, splitUsername.length() - 1);
    }

    // 操作聊天室的返回结果转成 ChatRoom 对象
    private ChatRoom responseToChatRoomObject(String roomId, JsonNode response) throws ChatRoomsException {
        JsonNode data = response.get("data");
        if (data == null) {
            throw new ChatRoomsException("data is null");
        }

        Object dataObject;
        try {
            dataObject = this.mapper.treeToValue(data, Object.class);
        } catch (JsonProcessingException e) {
            throw new ChatRoomsException("data to object fail " + e);
        }

        Long timestamp;
        if (response.get("timestamp") != null) {
            timestamp = response.get("timestamp").asLong();
        } else {
            timestamp = null;
        }

        String cursor;
        Integer count;

        if (response.get("cursor") != null) {
            cursor = response.get("cursor").asText();
        } else {
            cursor = null;
        }

        if (response.get("count") != null) {
            count = response.get("count").asInt();
        } else {
            count = null;
        }
        return ChatRoom.builder()
                .roomId(roomId)
                .data(dataObject)
                .cursor(cursor)
                .count(count)
                .timeStamp(timestamp)
                .build();
    }

}
