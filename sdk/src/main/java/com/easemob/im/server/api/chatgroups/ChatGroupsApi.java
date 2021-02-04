package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.ApiException;
import com.easemob.im.server.api.chatgroups.exception.ChatGroupsException;
import com.easemob.im.server.model.ChatGroup;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

public class ChatGroupsApi {

    private static final Pattern VALID_CHAT_GROUP_ID_PATTERN = Pattern.compile("[1-9][0-9]+");

    private static final Pattern VALID_CHAT_GROUP_USERNAME_PATTERN = Pattern.compile("[A-Za-z-0-9]{1,64}");

    private final HttpClient http;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    private final EMProperties properties;

    private final Cache<String, String> tokenCache;

    public ChatGroupsApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator, EMProperties properties, Cache<String, String> tokenCache) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
        this.properties = properties;
        this.tokenCache = tokenCache;
    }

    /**
     * 获取群组公告
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E5%85%AC%E5%91%8A
     *
     * 获取指定群组id的群组公告
     *
     * @param groupId  需要获取群组公告的群组 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getChatGroupAnnouncement(String groupId) throws ChatGroupsException {
        verifyGroupId(groupId);

        String uri = "/chatgroups/" + groupId + "/announcement";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 修改群组公告
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E4%BF%AE%E6%94%B9%E7%BE%A4%E7%BB%84%E5%85%AC%E5%91%8A
     *
     * 修改指定群组id的群组公告，注意群组公告的内容不能超过512个字符
     *
     * @param groupId       需要修改群组公告的群组 ID
     * @param announcement  要修改的群组公告内容
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup modifyChatGroupAnnouncement(String groupId, String announcement) throws ChatGroupsException {
        verifyGroupId(groupId);
        if (announcement == null || announcement.length() > 512) {
            throw new ChatGroupsException("Bad Request invalid announcement");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("announcement", announcement);

        String uri = "/chatgroups/" + groupId + "/announcement";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取群组共享文件
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E5%85%B1%E4%BA%AB%E6%96%87%E4%BB%B6
     *
     * 分页获取指定群组id的群组共享文件，之后可以根据response中返回的file_id，file_id是群组共享文件的唯一标识，
     * 调用 "下载群共享文件" 接口下载文件，调用 "删除群共享文件" 接口删除文件
     *
     * @param groupId   需要获取群组共享文件的群组 ID
     * @param pageNum   要获取第几页，默认是从第1页开始获取
     * @param pageSize  每页获取多少条，每页最多可以获取1000条
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getChatGroupShareFile(String groupId, Integer pageNum, Integer pageSize) throws ChatGroupsException {
        verifyGroupId(groupId);
        if (pageNum == null || pageNum < 0) {
            throw new ChatGroupsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize > 1000 || pageSize < 0) {
            throw new ChatGroupsException("Bad Request invalid pageSize");
        }

        String uri = "/chatgroups/" + groupId + "/share_files?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取单个群组共享文件
     * @param groupId  需要获取群组共享文件的群组 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getChatGroupShareFile(String groupId) throws ChatGroupsException {
        verifyGroupId(groupId);

        String uri = "/chatgroups/" + groupId + "/share_files";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 上传群组共享文件
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E4%B8%8A%E4%BC%A0%E7%BE%A4%E7%BB%84%E5%85%B1%E4%BA%AB%E6%96%87%E4%BB%B6
     *
     * 上传指定群组id的群组共享文件。注意上传的文件大小不能超过10MB
     *
     * @param groupId  需要上传群组共享文件的群组 ID
     * @param file     需要上传的共享文件（文件可以包括图片，语音，视频，文件）
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup uploadChatGroupShareFile(String groupId, File file) throws ChatGroupsException {
        verifyGroupId(groupId);

        HttpClient client;
        client = this.http.headers(h -> h.add("restrict-access", "true"));

        String uri = "/chatgroups/" + groupId + "/share_files";
        JsonNode response;
        try {
            response = HttpUtils.upload(client, uri, file, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 下载群组共享文件
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E4%B8%8B%E8%BD%BD%E7%BE%A4%E7%BB%84%E5%85%B1%E4%BA%AB%E6%96%87%E4%BB%B6
     * 根据指定的群组id与
     * @param groupId                       需要下载群组共享文件的群组 ID
     * @param fileId                        fileId下载群组共享文件，fileId是通过 "获取群组共享文件" 接口获取到的
     * @param assignDownloadAttachmentPath  指定群组共享文件要下载到的路径，例如 /xx/.../file/
     * @param assignDownloadAttachmentName  指定下载群组共享文件的名称，要加后缀，比如下载的图片就是 imageName.jpg , imageName.png
     * @return JsonNode
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public JsonNode downloadChatGroupShareFile(String groupId, String fileId, String assignDownloadAttachmentPath, String assignDownloadAttachmentName) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyFileId(fileId);
        verifyAssignDownloadPath(assignDownloadAttachmentPath);
        verifyAssignDownLoadName(assignDownloadAttachmentName);

        HttpClient client =this.http.headers(h -> h.add("Accept", "application/octet-stream"));

        String uri = "/chatgroups/" + groupId + "/share_files/" + fileId;
        try {
            return HttpUtils.download(client, uri, assignDownloadAttachmentPath, assignDownloadAttachmentName, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
    }

    /**
     * 删除群组共享文件
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E5%88%A0%E9%99%A4%E7%BE%A4%E7%BB%84%E5%85%B1%E4%BA%AB%E6%96%87%E4%BB%B6
     *
     * 根据指定的群组id与file_id删除群组共享文件，file_id是通过 "获取群组共享文件" 接口获取到的
     *
     * @param groupId  需要删除群组共享文件的群组 ID
     * @param fileId   需要删除群组共享文件 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup deleteChatGroupShareFile(String groupId, String fileId) throws ChatGroupsException {
        verifyGroupId(groupId);
        if (fileId == null || fileId.length() < 1) {
            throw new ChatGroupsException("Bad Request invalid fileId");
        }

        String uri = "/chatgroups/" + groupId + "/share_files/" + fileId;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 分页获取群组成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * 可以分页获取群组成员列表的接口
     *
     * @param groupId   需要获取的群组 ID
     * @param pageNum   要获取第几页
     * @param pageSize  每页获取多少条
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getChatGroupMembers(String groupId, Integer pageNum, Integer pageSize) throws ChatGroupsException {
        verifyGroupId(groupId);
        if (pageNum == null || pageNum < 0) {
            throw new ChatGroupsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize < 0) {
            throw new ChatGroupsException("Bad Request invalid pageSize");
        }

        String uri = "/chatgroups/" + groupId + "/users?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加单个群组成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * 一次给群添加一个成员，不同重复添加同一个成员。如果用户已经是群成员，将添加失败，并返回错误
     *
     * @param groupId   需要添加的群成员的群组 ID
     * @param username  需要添加的 IM 用户名
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup addChatGroupMember(String groupId, String username) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/users/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 批量添加群组成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%89%B9%E9%87%8F%E6%B7%BB%E5%8A%A0%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * 为群组添加多个成员，一次最多可以添加60位成员。如果所有用户均已是群成员，将添加失败，并返回错误
     *
     * @param groupId    需要添加的群组 ID
     * @param usernames  需要添加到群组的用户ID列表
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup batchAddChatGroupMember(String groupId, Set<String> usernames) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsernames(usernames);
        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/chatgroups/" + groupId + "/users";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 移除单个群组成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E5%8D%95%E4%B8%AA%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * 从群中移除某个成员。如果被移除用户不是群成员，将移除失败，并返回错误
     *
     * @param groupId   需要移除用户的群组 ID
     * @param username  需要移除的 IM 用户名
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup deleteChatGroupMember(String groupId, String username) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/users/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 批量移除群组成员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%89%B9%E9%87%8F%E7%A7%BB%E9%99%A4%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98
     *
     * @param groupId   需要移除用户的群组 ID
     * @param members  需要移除的 IM 用户列表
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup batchDeleteChatGroupMember(String groupId, Set<String> members) throws ChatGroupsException {
        verifyGroupId(groupId);
        String splitMember = verifySplitUsernames(members);

        String uri = "/chatgroups/" + groupId + "/users/" + splitMember;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取群管理员列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8
     *
     * @param groupId  需要获取的群组 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getChatGroupAdminList(String groupId) throws ChatGroupsException {
        verifyGroupId(groupId);

        String uri = "/chatgroups/" + groupId + "/admin";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加群管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 将一个群成员角色提升为群管理员接口
     *
     * @param groupId   需要添加管理员的群组 ID
     * @param newAdmin  需要添加为管理员的用户 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup addChatGroupAdmin(String groupId, String newAdmin) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(newAdmin);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newadmin", newAdmin);

        String uri = "/chatgroups/" + groupId + "/admin";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 移除群管理员
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98
     *
     * 将用户的角色从群管理员降为群普通成员接口
     *
     * @param groupId   需要移除的管理员所在群组 ID
     * @param oldAdmin  需要移除的管理员的用户 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup removeChatGroupAdmin(String groupId, String oldAdmin) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(oldAdmin);

        String uri = "/chatgroups/" + groupId + "/admin/" + oldAdmin;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 转让群组
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%BD%AC%E8%AE%A9%E7%BE%A4%E7%BB%84
     *
     * 修改群组 Owner 为同一群组中的其他成员
     *
     * @param groupId   需要转让的群组 ID
     * @param newOwner  被转让群主的用户 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup transferChatGroupAdmin(String groupId, String newOwner) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(newOwner);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newowner", newOwner);

        String uri = "/chatgroups/" + groupId;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 查询群组黑名单
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%9F%A5%E8%AF%A2%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95
     *
     * 查询一个群组黑名单中的用户列表。位于黑名单中的用户查看不到该群组的信息，也无法收到该群组的消息
     *
     * @param groupId  需要查询的群组 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getChatGroupBlocks(String groupId) throws ChatGroupsException {
        verifyGroupId(groupId);

        String uri = "/chatgroups/" + groupId + "/blocks/users";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加单个用户至群组黑名单
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7%E8%87%B3%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95
     *
     * 添加一个用户进入一个群组的黑名单。群主无法被加入群组的黑名单
     *
     * @param groupId   需要添加黑名单的群组 ID
     * @param username  要添加的 IM 用户名
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup addUserToChatGroupBlocks(String groupId, String username) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/blocks/users/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 批量添加用户至群组黑名单
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%89%B9%E9%87%8F%E6%B7%BB%E5%8A%A0%E7%94%A8%E6%88%B7%E8%87%B3%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95
     *
     * 添加多个用户进入一个群组的黑名单，一次性最多可以添加60个用户。群主无法被加入群组的黑名单
     *
     * @param groupId    需要添加黑名单的群组 ID
     * @param usernames  要添加的 IM 用户列表
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup batchAddUserToChatGroupBlocks(String groupId, Set<String> usernames) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsernames(usernames);
        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/chatgroups/" + groupId + "/blocks/users";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 从群组黑名单移除单个用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E4%BB%8E%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95%E7%A7%BB%E9%99%A4%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7
     *
     * 从群组黑名单中移除一个用户。对于群组黑名单中的用户，如果需要将其再次加入群组，需要先将其从群组黑名单中移除。
     *
     * @param groupId   需要移除黑名单的群组 ID
     * @param username  要移除的 IM 用户名
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup removeUserToChatGroupBlocks(String groupId, String username) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/blocks/users/" + username;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 批量从群组黑名单移除用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%89%B9%E9%87%8F%E4%BB%8E%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95%E7%A7%BB%E9%99%A4%E7%94%A8%E6%88%B7
     *
     * 从群组的黑名单中移除多个用户，一次性最多可以移除60个用户
     *
     * @param groupId    需要移除黑名单的群组 ID
     * @param usernames  要移除的 IM 用户列表
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup batchRemoveUserToChatGroupBlocks(String groupId, Set<String> usernames) throws ChatGroupsException {
        verifyGroupId(groupId);
        String splitUsername = verifySplitUsernames(usernames);

        String uri = "/chatgroups/" + groupId + "/blocks/users/" + splitUsername;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E7%A6%81%E8%A8%80
     *
     * 将多个用户禁言。用户被禁言后，将无法在群中发送消息
     *
     * @param groupId       需要添加禁言的群组 ID
     * @param username      要被禁言的 IM 用户名
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup addMute(String groupId, String username, Long muteDuration) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.createArrayNode().addPOJO(username));
        request.put("mute_duration", muteDuration);

        String uri = "/chatgroups/" + groupId + "/mute";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加多个用户禁言
     *
     * @param groupId    需要添加禁言的群组 ID
     * @param usernames  要添加禁言的 IM 用户列表
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup addMute(String groupId, Set<String> usernames, Long muteDuration) throws ChatGroupsException {
        verifyGroupId(groupId);
        if (usernames == null || usernames.size() < 1) {
            throw new ChatGroupsException("Bad Request invalid usernames");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));
        request.put("mute_duration", muteDuration);

        String uri = "/chatgroups/" + groupId + "/mute";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 移除单个用户禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E7%A6%81%E8%A8%80
     *
     * 将用户从禁言列表中移除。移除后，用户可以正常在群中发送消息
     *
     * @param groupId  需要移除禁言的群组 ID
     * @param member   需要移除禁言的用户 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup removeMute(String groupId, String member) throws ChatGroupsException {
        verifyGroupId(groupId);
        verifyUsername(member);

        String uri = "/chatgroups/" + groupId + "/mute/" + member;
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 移除多个用户禁言
     *
     * @param groupId  需要移除禁言的群组 ID
     * @param members  需要移除禁言的用户 ID列表
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup removeMute(String groupId, Set<String> members) throws ChatGroupsException {
        verifyGroupId(groupId);
        if (members == null || members.size() < 1) {
            throw new ChatGroupsException("Bad Request invalid members");
        }

        StringBuilder splitMember = new StringBuilder();
        for (String member : members) {
            verifyUsername(member);
            splitMember.append(member).append(",");
        }

        String uri = "/chatgroups/" + groupId + "/mute/" + splitMember.substring(0, splitMember.length() - 1);
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取禁言列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%A6%81%E8%A8%80%E5%88%97%E8%A1%A8
     *
     * @param groupId  需要添加禁言列表的群组 ID
     * @return ChatGroup
     * @throws ChatGroupsException 调用群组方法会抛出的异常
     */
    public ChatGroup getMuteList(String groupId) throws ChatGroupsException {
        verifyGroupId(groupId);
        String uri = "/chatgroups/" + groupId + "/mute";
        JsonNode response;
        try {
            response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        } catch (ApiException e) {
            throw new ChatGroupsException(e.getMessage());
        }
        return responseToChatGroupObject(groupId, response);
    }

    // 验证 username
    private void verifyUsername(String username) throws ChatGroupsException {
        if (username == null || !VALID_CHAT_GROUP_USERNAME_PATTERN.matcher(username).matches()) {
            throw new ChatGroupsException(String.format("Bad Request %s invalid username", username));
        }
    }

    // 验证 cursor
    private void verifyCursor(String cursor) throws ChatGroupsException {
        if (cursor == null || cursor.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid cursor");
        }
    }

    // 验证 groupId
    private void verifyGroupId(String groupId) throws ChatGroupsException {
        if (groupId == null || !VALID_CHAT_GROUP_ID_PATTERN.matcher(groupId).matches()) {
            throw new ChatGroupsException("Bad Request invalid groupId");
        }
    }

    // 验证 usernames
    private void verifyUsernames(Set<String> usernames) throws ChatGroupsException {
        if (usernames == null || usernames.size() < 1 || usernames.size() > 60) {
            throw new ChatGroupsException("Bad Request invalid usernames");
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

    // 验证 limit
    private void verifyLimit(int limit) throws ChatGroupsException {
        if (limit < 1) {
            throw new ChatGroupsException("Bad Request invalid limit");
        }
    }

    // 验证 file id
    private void verifyFileId(String fileId) throws ChatGroupsException {
        if (fileId == null || fileId.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid fileId");
        }
    }

    // 验证 assign download path
    private void verifyAssignDownloadPath(String assignDownloadPath) throws ChatGroupsException {
        if (assignDownloadPath == null || assignDownloadPath.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid assignDownloadPath");
        }
    }

    // 验证 assign download name
    private void verifyAssignDownLoadName(String assignDownloadName) throws ChatGroupsException {
        if (assignDownloadName == null || assignDownloadName.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid assignDownloadName");
        }
    }

    // 操作群组的返回结果转成 ChatGroup 对象
    private ChatGroup responseToChatGroupObject(String groupId, JsonNode response) throws ChatGroupsException {
        JsonNode data = response.get("data");
        if (data == null) {
            throw new ChatGroupsException("data is null");
        }

        Object dataObject;
        try {
            dataObject = this.mapper.treeToValue(data, Object.class);
        } catch (JsonProcessingException e) {
            throw new ChatGroupsException("data to object fail " + e);
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
        return ChatGroup.builder()
                .groupId(groupId)
                .data(dataObject)
                .cursor(cursor)
                .count(count)
                .timeStamp(timestamp)
                .build();
    }

}
