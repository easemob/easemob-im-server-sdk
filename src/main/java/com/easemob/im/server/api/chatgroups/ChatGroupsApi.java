package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.api.chatgroups.exception.ChatGroupsException;
import com.easemob.im.server.model.ChatGroup;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public ChatGroupsApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
    }

    /**
     * 获取App中所有的群组
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96app%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E7%BE%A4%E7%BB%84_%E5%8F%AF%E5%88%86%E9%A1%B5
     *
     * 分页获取应用下全部的群组信息的接口
     *
     * @param limit   要获取的群组数量
     * @param cursor  游标
     * @return ChatGroup
     */
    public ChatGroup getAppAllChatGroup(int limit, String cursor) {
        verifyLimit(limit);

        String uri;
        // 第一次删除不需要传cursor
        if (cursor != null) {
            verifyCursor(cursor);
            uri = "/chatgroups?limit=" + limit + "&cursor=" + cursor;
        } else {
            uri = "/chatgroups?limit=" + limit;
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
        return responseToChatGroupObject(null, response);
    }

    /**
     * 获取一个用户参与的所有群组
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E4%B8%80%E4%B8%AA%E7%94%A8%E6%88%B7%E5%8F%82%E4%B8%8E%E7%9A%84%E6%89%80%E6%9C%89%E7%BE%A4%E7%BB%84
     *
     * 根据用户名称获取该用户加入的全部群组接口
     *
     * @param username  需要获取的 IM 用户名
     * @return ChatGroup
     */
    public ChatGroup getUserJoinAllChatGroup(String username) {
        verifyUsername(username);
        String uri = "/users/" + username + "/joined_chatgroups";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
        return responseToChatGroupObject(null, response);
    }

    /**
     * 获取群组详情
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E8%AF%A6%E6%83%85
     *
     * 获取多个群组的详情。当获取多个群组的详情时，会返回所有存在的群组的详情，对于不存在的群组，response body内返回“group id doesn't exist”
     *
     * @param groupIds  需要获取的群组 ID列表
     * @return ChatGroup
     */
    public ChatGroup getChatGroupDetails(Set<String> groupIds) {
        if (groupIds == null) {
            throw new ChatGroupsException("Bad Request groupIds is null");
        }

        StringBuilder splitGroupId = new StringBuilder();
        for (String groupId : groupIds) {
            splitGroupId.append(groupId).append(",");
        }

        String uri = "/chatgroups/" + splitGroupId.substring(0, splitGroupId.length() - 1);
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
        return responseToChatGroupObject(null, response);
    }

    /**
     * 获取单个群组详情
     *
     * @param groupId  需要获取的群组 ID
     * @return JsonNode
     */
    public ChatGroup getChatGroupDetails(String groupId) {
        verifyGroupId(groupId);
        String uri = "/chatgroups/" + groupId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 创建一个群组
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%BE%A4%E7%BB%84
     *
     * 创建一个群组，并设置群组名称、群组描述、公开群/私有群属性、群成员最大人数（包括群主）、加入公开群是否需要批准、群主、以及群成员。
     *
     * @param groupName     群组名称，此属性为必须的
     * @param description   群组描述，此属性为必须的
     * @param isPublic      是否是公开群，此属性为必须的
     * @param maxUsers      群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
     * @param allowInvites  是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。
     *                      注：如果是公开群（public为true），则不允许群成员邀请别人加入此群
     * @param membersOnly   用户申请入群是否需要群主或者群管理员审批，默认是false。
     *                      注：如果允许了群成员邀请用户进群（allowinvites为true），那么就不需要群主或群管理员审批了
     * @param owner         群组的管理员，此属性为必须的
     * @param members       群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主user1不需要写入到members里面）
     * @return String groupId
     */
    public String createChatGroup(String groupName, String description, Boolean isPublic, Integer maxUsers,
                                    Boolean allowInvites, Boolean membersOnly, String owner, Set<String> members) {
        if (groupName == null) {
            throw new ChatGroupsException("Bad Request groupName is null");
        }

        if (description == null) {
            throw new ChatGroupsException("Bad Request description is null");
        }

        if (isPublic == null) {
            throw new ChatGroupsException("Bad Request public is null");
        }

        if (maxUsers != null) {
            if (maxUsers > 2000 || maxUsers < 1) {
                throw new ChatGroupsException("Bad Request invalid maxUsers");
            }
        }

        verifyUsername(owner);

        if (members != null) {
            if (members.size() < 1) {
                throw new ChatGroupsException("Bad Request invalid members");
            }
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("groupname", groupName);
        request.put("desc", description);
        request.put("public", isPublic);
        if (maxUsers != null) {
            request.put("maxusers", maxUsers);
        }
        if (allowInvites != null) {
            request.put("allowinvites", allowInvites);
        }
        if (membersOnly != null) {
            request.put("members_only", membersOnly);
        }
        request.put("owner", owner);

        if (members != null) {
            for (String member : members) {
                verifyUsername(member);
            }
            request.set("members", this.mapper.valueToTree(members));
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, "/chatgroups", request ,this.allocator, this.mapper);
        JsonNode data = response.get("data");
        if (data != null) {
            JsonNode groupId = data.get("groupid");
            if (groupId != null) {
                return groupId.asText();
            } else {
                throw new ChatGroupsException("response groupId is null");
            }
        } else {
            throw new ChatGroupsException("response data is null");
        }
    }

    /**
     * 修改群组信息
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E4%BF%AE%E6%94%B9%E7%BE%A4%E7%BB%84%E4%BF%A1%E6%81%AF
     *
     * 修改成功的数据行会返回 true，失败为 false。请求 body 只接收 groupname、description、maxusers、membersonly 四个属性
     *
     * @param groupId       群组id
     * @param groupName     群组名称
     * @param description   群组描述
     * @param maxUsers      群组成员最大数（包括群主）
     * @param membersOnly   加入群组是否需要群主或者群管理员审批。true：是，false：否
     * @param allowInvites  是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主才可以往群里加人
     * @return ChatGroup
     */
    public ChatGroup modifyChatGroupInfo(String groupId, String groupName, String description, Integer maxUsers, Boolean membersOnly, Boolean allowInvites) {
        verifyGroupId(groupId);

        if (maxUsers != null) {
            if (maxUsers > 2000) {
                throw new ChatGroupsException("Bad Request maxUsers exceed 2000 limit");
            }
        }

        ObjectNode request = this.mapper.createObjectNode();
        if (groupName != null) {
            request.put("groupname", groupName);
        }

        if (description != null) {
            request.put("description", description);
        }

        if (maxUsers != null) {
            request.put("maxusers", maxUsers);
        }

        if (membersOnly != null) {
            request.put("membersonly", membersOnly);
        }

        if (allowInvites != null) {
            request.put("allowinvites", allowInvites);
        }

        String uri = "/chatgroups/" + groupId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request ,this.allocator, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 删除群组
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E5%88%A0%E9%99%A4%E7%BE%A4%E7%BB%84
     *
     * 删除一个群组的接口
     *
     * @param groupId  需要删除的群组 ID
     * @return ChatGroup
     */
    public ChatGroup deleteChatGroup(String groupId) {
        verifyGroupId(groupId);
        String uri = "/chatgroups/" + groupId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
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
     */
    public ChatGroup getChatGroupAnnouncement(String groupId) {
        verifyGroupId(groupId);
        String uri = "/chatgroups/" + groupId + "/announcement";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
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
     */
    public ChatGroup modifyChatGroupAnnouncement(String groupId, String announcement) {
        verifyGroupId(groupId);
        if (announcement == null || announcement.length() > 512) {
            throw new ChatGroupsException("Bad Request invalid announcement");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("announcement", announcement);

        String uri = "/chatgroups/" + groupId + "/announcement";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper);
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
     */
    public ChatGroup getChatGroupShareFile(String groupId, Integer pageNum, Integer pageSize) {
        verifyGroupId(groupId);
        if (pageNum == null || pageNum < 0) {
            throw new ChatGroupsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize > 1000 || pageSize < 0) {
            throw new ChatGroupsException("Bad Request invalid pageSize");
        }

        String uri = "/chatgroups/" + groupId + "/share_files?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取单个群组共享文件
     * @param groupId  需要获取群组共享文件的群组 ID
     * @return ChatGroup
     */
    public ChatGroup getChatGroupShareFile(String groupId) {
        verifyGroupId(groupId);

        String uri = "/chatgroups/" + groupId + "/share_files";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
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
     */
    public ChatGroup uploadChatGroupShareFile(String groupId, File file) {
        verifyGroupId(groupId);

        HttpClient client;
        client = this.http.headers(h -> {
            h.add("restrict-access", "true");
        });

        String uri = "/chatgroups/" + groupId + "/share_files";
        JsonNode response = HttpUtils.upload(client, uri, file, this.mapper);
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
     * @return ChatGroup
     */
    public ChatGroup downloadChatGroupShareFile(String groupId, String fileId, String assignDownloadAttachmentPath, String assignDownloadAttachmentName) {
        verifyGroupId(groupId);
        verifyFileId(fileId);
        verifyAssignDownloadPath(assignDownloadAttachmentPath);
        verifyAssignDownLoadName(assignDownloadAttachmentName);

        HttpClient client =this.http.headers(h -> {
            h.add("Accept", "application/octet-stream");
        });

        String uri = "/chatgroups/" + groupId + "/share_files/" + fileId;
        JsonNode response = HttpUtils.download(client, uri, assignDownloadAttachmentPath, assignDownloadAttachmentName, this.mapper);
        return responseToChatGroupObject(groupId, response);
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
     */
    public ChatGroup deleteChatGroupShareFile(String groupId, String fileId) {
        verifyGroupId(groupId);
        if (fileId == null || fileId.length() < 1) {
            throw new ChatGroupsException("Bad Request invalid fileId");
        }

        String uri = "/chatgroups/" + groupId + "/share_files/" + fileId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
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
     */
    public ChatGroup getChatGroupMembers(String groupId, Integer pageNum, Integer pageSize) {
        verifyGroupId(groupId);
        if (pageNum == null || pageNum < 0) {
            throw new ChatGroupsException("Bad Request invalid pageNum");
        }
        if (pageSize == null || pageSize < 0) {
            throw new ChatGroupsException("Bad Request invalid pageSize");
        }

        String uri = "/chatgroups/" + groupId + "/users?pagenum=" + pageNum + "&pagesize=" + pageSize;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
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
     */
    public ChatGroup addChatGroupMember(String groupId, String username) {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper);
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
     */
    public ChatGroup batchAddChatGroupMember(String groupId, Set<String> usernames) {
        verifyGroupId(groupId);
        verifyUsernames(usernames);
        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/chatgroups/" + groupId + "/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper);
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
     */
    public ChatGroup deleteChatGroupMember(String groupId, String username) {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
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
     */
    public ChatGroup batchDeleteChatGroupMember(String groupId, Set<String> members) {
        verifyGroupId(groupId);
        String splitMember = verifySplitUsernames(members);

        String uri = "/chatgroups/" + groupId + "/users/" + splitMember;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取群管理员列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8
     *
     * @param groupId  需要获取的群组 ID
     * @return ChatGroup
     */
    public ChatGroup getChatGroupAdminList(String groupId) {
        verifyGroupId(groupId);
        String uri = "/chatgroups/" + groupId + "/admin";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
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
     */
    public ChatGroup addChatGroupAdmin(String groupId, String newAdmin) {
        verifyGroupId(groupId);
        verifyUsername(newAdmin);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newadmin", newAdmin);

        String uri = "/chatgroups/" + groupId + "/admin";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper);
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
     */
    public ChatGroup removeChatGroupAdmin(String groupId, String oldAdmin) {
        verifyGroupId(groupId);
        verifyUsername(oldAdmin);

        String uri = "/chatgroups/" + groupId + "/admin/" + oldAdmin;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
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
     */
    public ChatGroup transferChatGroupAdmin(String groupId, String newOwner) {
        verifyGroupId(groupId);
        verifyUsername(newOwner);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newowner", newOwner);

        String uri = "/chatgroups/" + groupId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request ,this.allocator, this.mapper);
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
     */
    public ChatGroup getChatGroupBlocks(String groupId) {
        verifyGroupId(groupId);

        String uri = "/chatgroups/" + groupId + "/blocks/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
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
     */
    public ChatGroup addUserToChatGroupBlocks(String groupId, String username) {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/blocks/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper);
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
     */
    public ChatGroup batchAddUserToChatGroupBlocks(String groupId, Set<String> usernames) {
        verifyGroupId(groupId);
        verifyUsernames(usernames);
        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/chatgroups/" + groupId + "/blocks/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper);
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
     */
    public ChatGroup removeUserToChatGroupBlocks(String groupId, String username) {
        verifyGroupId(groupId);
        verifyUsername(username);

        String uri = "/chatgroups/" + groupId + "/blocks/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
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
     */
    public ChatGroup batchRemoveUserToChatGroupBlocks(String groupId, Set<String> usernames) {
        verifyGroupId(groupId);
        String splitUsername = verifySplitUsernames(usernames);

        String uri = "/chatgroups/" + groupId + "/blocks/users/" + splitUsername;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E7%A6%81%E8%A8%80
     *
     * 将一个或多个用户禁言。用户被禁言后，将无法在群中发送消息
     *
     * @param groupId       需要添加禁言的群组 ID
     * @param username      要被禁言的 IM 用户名
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatGroup
     */
    public ChatGroup addMute(String groupId, String username, Long muteDuration) {
        verifyGroupId(groupId);
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.createArrayNode().addPOJO(username));
        request.put("mute_duration", muteDuration);

        String uri = "/chatgroups/" + groupId + "/mute";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 添加多个用户禁言
     *
     * @param groupId    需要添加禁言的群组 ID
     * @param usernames  要添加禁言的 IM 用户列表
     * @param muteDuration  禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为当前时间戳加上Long最大值）
     * @return ChatGroup
     */
    public ChatGroup addMute(String groupId, Set<String> usernames, Long muteDuration) {
        verifyGroupId(groupId);
        if (usernames == null || usernames.size() < 1) {
            throw new ChatGroupsException("Bad Request invalid usernames");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));
        request.put("mute_duration", muteDuration);

        String uri = "/chatgroups/" + groupId + "/mute";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request ,this.allocator, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 移除单个禁言
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E7%A6%81%E8%A8%80
     *
     * 将用户从禁言列表中移除。移除后，用户可以正常在群中发送消息
     *
     * @param groupId  需要移除禁言的群组 ID
     * @param member   需要移除禁言的用户 ID
     * @return ChatGroup
     */
    public ChatGroup removeMute(String groupId, String member) {
        verifyGroupId(groupId);
        verifyUsername(member);

        String uri = "/chatgroups/" + groupId + "/mute/" + member;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 移除多个用户禁言
     *
     * @param groupId  需要移除禁言的群组 ID
     * @param members  需要移除禁言的用户 ID列表
     * @return ChatGroup
     */
    public ChatGroup removeMute(String groupId, Set<String> members) {
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
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    /**
     * 获取禁言列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%A6%81%E8%A8%80%E5%88%97%E8%A1%A8
     *
     * @param groupId  需要添加禁言列表的群组 ID
     * @return ChatGroup
     */
    public ChatGroup getMuteList(String groupId) {
        verifyGroupId(groupId);
        String uri = "/chatgroups/" + groupId + "/mute";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);
        return responseToChatGroupObject(groupId, response);
    }

    // 验证 username
    private void verifyUsername(String username) {
        if (username == null || !VALID_CHAT_GROUP_USERNAME_PATTERN.matcher(username).matches()) {
            throw new ChatGroupsException(String.format("Bad Request %s invalid username", username));
        }
    }

    // 验证 cursor
    private void verifyCursor(String cursor) {
        if (cursor == null || cursor.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid cursor");
        }
    }

    // 验证 groupId
    private void verifyGroupId(String groupId) {
        if (groupId == null || !VALID_CHAT_GROUP_ID_PATTERN.matcher(groupId).matches()) {
            throw new ChatGroupsException("Bad Request invalid groupId");
        }
    }

    // 验证 usernames
    private void verifyUsernames(Set<String> usernames) {
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
    private void verifyLimit(int limit) {
        if (limit < 1) {
            throw new ChatGroupsException("Bad Request invalid limit");
        }
    }

    // 验证 file id
    private void verifyFileId(String fileId) {
        if (fileId == null || fileId.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid fileId");
        }
    }

    // 验证 assign download path
    private void verifyAssignDownloadPath(String assignDownloadPath) {
        if (assignDownloadPath == null || assignDownloadPath.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid assignDownloadPath");
        }
    }

    // 验证 assign download name
    private void verifyAssignDownLoadName(String assignDownloadName) {
        if (assignDownloadName == null || assignDownloadName.isEmpty()) {
            throw new ChatGroupsException("Bad Request invalid assignDownloadName");
        }
    }

    // 操作群组的返回结果转成 ChatGroup 对象
    private ChatGroup responseToChatGroupObject(String groupId, JsonNode response) {
        JsonNode data = response.get("data");
        if (data == null || data.size() < 1) {
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
