package com.easemob.im.server.api.chatrooms;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatgroups.exception.ChatGroupsException;
import com.easemob.im.server.api.chatrooms.exception.ChatRoomsException;
import com.easemob.im.server.model.ChatGroup;
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
     * 获取 APP 中所有的聊天室
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96_app_%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4
     *
     * @return ChatRoom
     */
    public ChatRoom getAppAllChatRoom() {
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, "/chatrooms", this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(null, response);
    }

    /**
     * 获取用户加入的聊天室
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%8A%A0%E5%85%A5%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4
     *
     * 根据用户名称获取该用户加入的全部聊天室接口
     *
     * @param username  需要获取的 IM 用户名
     * @return ChatRoom
     */
    public ChatRoom getUserJoinedChatRoom(String username) {
        verifyUsername(username);

        String uri = "/users/" + username + "/joined_chatrooms";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(null, response);
    }

    /**
     * 获取聊天室详情
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%AF%A6%E6%83%85
     *
     * 可以获取多个聊天室的详情。当获取多个聊天室的详情时，会返回所有存在的聊天室的详情，对于不存在的聊天室，response body内返回“chatroom id doesn't exist”
     *
     * @param roomIds  需要获取的聊天室 ID列表
     * @return ChatRoom
     */
    public ChatRoom getChatRoomDetails(Set<String> roomIds) {
        if (roomIds == null || roomIds.size() < 1) {
            throw new ChatGroupsException("Bad Request invalid roomIds");
        }

        StringBuilder splitRoomId = new StringBuilder();
        for (String groupId : roomIds) {
            splitRoomId.append(groupId).append(",");
        }

        String uri = "/chatrooms/" + splitRoomId.substring(0, splitRoomId.length() - 1);
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(null, response);
    }

    /**
     * 获取单个聊天室详情
     *
     * @param roomId  需要获取的聊天室 ID
     * @return ChatRoom
     */
    public ChatRoom getChatRoomDetails(String roomId) {
        verifyRoomId(roomId);
        String uri = "/chatrooms/" + roomId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 创建聊天室
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%9B%E5%BB%BA%E8%81%8A%E5%A4%A9%E5%AE%A4
     *
     * 创建一个聊天室，并设置聊天室名称、聊天室描述、公开聊天室/私有聊天室属性、聊天室成员最大人数（包括管理员）、加入公开聊天室是否需要批准、管理员、以及聊天室成员
     *
     * @param roomName      聊天室名称，此属性为必须的
     * @param description   聊天室描述，此属性为必须的
     * @param maxUsers      聊天室成员最大数（包括聊天室创建者），值为数值类型，默认值200，最大值5000，此属性为可选的
     * @param owner         聊天室的管理员，此属性为必须的
     * @param members       聊天室成员，此属性为可选的，但是如果加了此项，数组元素至少一个
     * @return String roomId
     */
    public String createChatRoom(String roomName, String description, Integer maxUsers, String owner, Set<String> members) {
        if (roomName == null) {
            throw new ChatRoomsException("Bad Request groupName is null");
        }

        if (description == null) {
            throw new ChatRoomsException("Bad Request description is null");
        }

        if (maxUsers != null) {
            if (maxUsers > 5000 || maxUsers < 1) {
                throw new ChatRoomsException("Bad Request invalid maxUsers");
            }
        }

        verifyUsername(owner);

        if (members != null) {
            if (members.size() < 1) {
                throw new ChatRoomsException("Bad Request invalid members");
            }
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("name", roomName);
        request.put("description", description);
        if (maxUsers != null) {
            request.put("maxusers", maxUsers);
        }

        request.put("owner", owner);

        if (members != null) {
            for (String member : members) {
                verifyUsername(member);
            }
            request.set("members", this.mapper.valueToTree(members));
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, "/chatrooms", request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        JsonNode data = response.get("data");
        if (data != null) {
            JsonNode roomId = data.get("id");
            if (roomId != null) {
                return roomId.asText();
            } else {
                throw new ChatRoomsException("response roomId is null");
            }
        } else {
            throw new ChatRoomsException("response data is null");
        }
    }

    /**
     * 修改聊天室信息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E4%BF%AE%E6%94%B9%E8%81%8A%E5%A4%A9%E5%AE%A4%E4%BF%A1%E6%81%AF
     *
     * 修改成功的数据行会返回 true，失败为 false。请求 body 只接收 name、description、maxusers 三个属性。传其他字段，或者不能修改的字段会抛异常
     *
     * @param roomId        聊天室id
     * @param roomName      聊天室名称
     * @param description   聊天室描述
     * @param maxUsers      聊天室成员最大数
     * @return ChatRoom
     */
    public ChatRoom modifyChatRoomInfo(String roomId, String roomName, String description, Integer maxUsers) {
        verifyRoomId(roomId);

        if (maxUsers != null) {
            if (maxUsers > 5000 || maxUsers < 1) {
                throw new ChatRoomsException("Bad Request invalid maxUsers");
            }
        }

        ObjectNode request = this.mapper.createObjectNode();
        if (roomName != null) {
            request.put("name", roomName);
        }

        if (description != null) {
            request.put("description", description);
        }

        if (maxUsers != null) {
            request.put("maxusers", maxUsers);
        }

        String uri = "/chatrooms/" + roomId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 删除聊天室
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%A0%E9%99%A4%E8%81%8A%E5%A4%A9%E5%AE%A4
     *
     * 删除单个聊天室。如果被删除的聊天室不存在，会返回错误
     *
     * @param roomId  需要删除的聊天室 ID
     * @return ChatRoom
     */
    public ChatRoom deleteChatRoom(String roomId) {
        verifyRoomId(roomId);
        String uri = "/chatrooms/" + roomId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
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
     */
    public ChatRoom getChatRoomMembers(String roomId, Integer pageNum, Integer pageSize) {
        verifyRoomId(roomId);
        if (pageNum == null || pageNum < 0) {
            throw new ChatRoomsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize < 0) {
            throw new ChatRoomsException("Bad Request invalid pageSize");
        }

        String uri = "/chatrooms/" + roomId + "/users?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加单个聊天室成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * 一次给聊天室添加一个成员，不同重复添加同一个成员。如果用户已经是聊天室成员，将添加失败，并返回错误
     *
     * @param roomId   需要添加的聊天室成员的群组 ID
     * @param username  需要添加的 IM 用户名
     * @return ChatRoom
     */
    public ChatRoom addChatRoomMember(String roomId, String username) {
        verifyRoomId(roomId);
        verifyUsername(username);

        String uri = "/chatrooms/" + roomId + "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);
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
     */
    public ChatRoom batchAddChatRoomMember(String roomId, Set<String> usernames) {
        verifyRoomId(roomId);
        verifyUsernames(usernames);
        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/chatrooms/" + roomId + "/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
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
     */
    public ChatRoom deleteChatRoomMember(String roomId, String username) {
        verifyRoomId(roomId);
        verifyUsername(username);

        String uri = "/chatrooms/" + roomId + "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 批量移除群组成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%89%B9%E9%87%8F%E7%A7%BB%E9%99%A4%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * @param roomId   需要移除用户的聊天室 ID
     * @param members  需要移除的 IM 用户列表
     * @return ChatRoom
     */
    public ChatRoom batchDeleteChatRoomMember(String roomId, Set<String> members) {
        verifyRoomId(roomId);
        String splitMember = verifySplitUsernames(members);

        String uri = "/chatrooms/" + roomId + "/users/" + splitMember;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 获取聊天室管理员列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8
     *
     * @param roomId  需要获取的聊天室 ID
     * @return JsonNode
     */
    public ChatRoom getChatRoomAdminList(String roomId) {
        verifyRoomId(roomId);
        String uri = "/chatrooms/" + roomId + "/admin";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加聊天室管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 将一个聊天室成员角色提升为聊天室管理员
     *
     * @param roomId   需要添加管理员的群组 ID
     * @param newAdmin  需要添加为管理员的用户 ID
     * @return ChatRoom
     */
    public ChatRoom addChatGroupAdmin(String roomId, String newAdmin) {
        verifyRoomId(roomId);
        verifyUsername(newAdmin);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newadmin", newAdmin);

        String uri = "/chatrooms/" + roomId + "/admin";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 移除聊天室管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 将用户的角色从聊天室管理员降为普通聊天室成员
     *
     * @param roomId   需要移除的管理员所在聊天室 ID
     * @param oldAdmin  需要移除的管理员的用户 ID
     * @return JsonNode
     */
    public ChatRoom removeChatRoomAdmin(String roomId, String oldAdmin) {
        verifyRoomId(roomId);
        verifyUsername(oldAdmin);

        String uri = "/chatrooms/" + roomId + "/admin/" + oldAdmin;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E7%A6%81%E8%A8%80
     *
     * 将一个用户禁言。用户被禁言后，将无法在聊天室中发送消息
     *
     * @param roomId        需要添加禁言的聊天室 ID
     * @param username      要被禁言的 IM 用户名
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatRoom
     */
    public ChatRoom addMute(String roomId, String username, Long muteDuration) {
        verifyRoomId(roomId);
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.createArrayNode().addPOJO(username));
        request.put("mute_duration", muteDuration);

        String uri = "/chatrooms/" + roomId + "/mute";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 添加多个用户禁言
     *
     * @param roomId    需要添加禁言的聊天室 ID
     * @param usernames  要被禁言的 IM 用户列表
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatRoom
     */
    public ChatRoom addMute(String roomId, Set<String> usernames, Long muteDuration) {
        verifyRoomId(roomId);
        if (usernames == null || usernames.size() < 1) {
            throw new ChatRoomsException("Bad Request invalid usernames");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));
        request.put("mute_duration", muteDuration);

        String uri = "/chatrooms/" + roomId + "/mute";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 移除单个禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E7%A6%81%E8%A8%80
     *
     * 将用户从禁言列表中移除。移除后，用户可以正常在聊天室中发送消息
     *
     * @param roomId   需要移除禁言的聊天室 ID
     * @param member   需要移除禁言的用户 ID
     * @return ChatRoom
     */
    public ChatRoom removeMute(String roomId, String member) {
        verifyRoomId(roomId);
        verifyUsername(member);

        String uri = "/chatrooms/" + roomId + "/mute/" + member;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 移除多个用户禁言
     *
     * @param roomId   需要移除禁言的聊天室 ID
     * @param members  需要移除禁言的用户 ID列表
     * @return ChatRoom
     */
    public ChatRoom removeMute(String roomId, Set<String> members) {
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
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(roomId, response);
    }

    /**
     * 获取禁言列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E7%A6%81%E8%A8%80%E5%88%97%E8%A1%A8
     *
     * @param roomId  需要添加禁言列表的聊天室 ID
     * @return ChatRoom
     */
    public ChatRoom getMuteList(String roomId) {
        verifyRoomId(roomId);
        String uri = "/chatrooms/" + roomId + "/mute";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
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
     */
    public ChatRoom getChatRoomSuperAdminList(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 0) {
            throw new ChatRoomsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize < 0) {
            throw new ChatRoomsException("Bad Request invalid pageSize");
        }

        String uri = "/chatrooms/super_admin?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
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
     */
    public ChatRoom addChatRoomSuperAdmin(String username) {
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("superadmin", username);

        String uri = "/chatrooms/super_admin";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(null, response);
    }

    /**
     * 移除超级管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98
     *
     * @param username  需要移除的 IM 用户名
     * @return ChatRoom
     */
    public ChatRoom removeChatRoomSuperAdmin(String username) {
        verifyUsername(username);
        String uri = "/chatrooms/super_admin/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToChatRoomObject(null, response);
    }

    // 验证 username
    private void verifyUsername(String username) {
        if (username == null || !VALID_CHAT_ROOM_USERNAME_PATTERN.matcher(username).matches()) {
            throw new ChatRoomsException(String.format("Bad Request %s invalid username", username));
        }
    }

    // 验证 roomId
    private void verifyRoomId(String roomId) {
        if (roomId == null || !VALID_CHAT_ROOM_ID_PATTERN.matcher(roomId).matches()) {
            throw new ChatRoomsException("Bad Request invalid roomId");
        }
    }

    // 验证 usernames
    private void verifyUsernames(Set<String> usernames) {
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
    private ChatRoom responseToChatRoomObject(String roomId, JsonNode response) {
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
