package com.easemob.im.server.api.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.exception.*;
import com.easemob.im.server.model.OperationUserEvent;
import com.easemob.im.server.model.User;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

import java.util.*;
import java.util.regex.Pattern;

public class UserApi {

    private static final Pattern VALID_USERNAME_PATTERN = Pattern.compile("[A-Za-z-0-9]{1,64}");

    private static final Pattern VALID_PASSWORD_PATTERN = Pattern.compile(".{1,64}");

    private static final Pattern VALID_NICKNAME_PATTERN = Pattern.compile(".{1,100}");

    private static final Pattern VALID_DISPLAY_STYLE_PATTERN = Pattern.compile("[0-1]");

    private static final Pattern VALID_MESSAGE_ID_PATTERN = Pattern.compile("[1-9][0-9]+");

    private static final Pattern VALID_NO_DISTURBING_START_PATTERN = Pattern.compile("^1[0-9]$|^2[0-4]$|^[0-9]$");

    private static final Pattern VALID_NO_DISTURBING_END_PATTERN = Pattern.compile("^1[0-9]$|^2[0-4]$|^[0-9]$");


    private final HttpClient http;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    private final EMProperties properties;

    private final Cache<String, String> tokenCache;

    public UserApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator, EMProperties properties, Cache<String, String> tokenCache) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
        this.properties = properties;
        this.tokenCache = tokenCache;
    }

    /**
     * 授权注册单个用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%B3%A8%E5%86%8C%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7_%E6%8E%88%E6%9D%83
     *
     * @param username 环信 ID，也就是 IM 用户的唯一登录账号，长度不可超过64个字符
     *
     * @param password 在客户端登录环信服务器使用的密码，长度不可超过64个字符
     *
     * @param nickname 昵称（可选），在 iOS Apns 推送时会使用的昵称（仅在推送通知栏内显示的昵称），
     *                 并不是用户个人信息的昵称，环信目前是不保存用户昵称，头像等个人信息的，
     *                 需要自己服务器保存并与给自己用户注册的 IM 用户名绑定，长度不可超过100个字符
     * @return User
     */
    public User register(String username, String password, String nickname) {
        verifyUsername(username);
        verifyPassword(password);
        if (nickname != null) {
            verifyNickname(nickname);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("username", username);
        request.put("password", password);
        if (nickname != null) {
            request.put("nickname", nickname);
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, "/users", request, this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.registerUser, username, response);
    }

    /**
     * 批量授权注册用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E6%B3%A8%E5%86%8C%E7%94%A8%E6%88%B7
     *
     * @param users 注册用户信息对象的数组，建议每次请求传入数组用户对象的数量不要超过20个
     * @return User
     */
    public User batchRegister(Set<BatchRegisterUser> users) {
        if (users == null || users.size() < 1) {
            throw new UserException("Bad Request invalid users");
        }

        ArrayNode request = this.mapper.createArrayNode();
        for (BatchRegisterUser user : users) {
            verifyUsername(user.getUsername());
            verifyPassword(user.getPassword());
            verifyNickname(user.getNickname());
            request.addPOJO(user);
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, "/users", request ,this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.batchRegisterUser, null, response);
    }

    /**
     * 获取单个用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7
     *
     * 获取单个 IM 用户的详细信息
     *
     * @param username 用户的username
     * @return User
     */
    public User getUser(String username) {
        verifyUsername(username);
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, "/users/" + username, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.getUser, username, response);
    }

    /**
     * 批量获取用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7
     *
     * 该接口默认返回按照创建时间排序，如果需要指定获取数量，需加上参数 limit=N，N 为数量值。
     * 关于分页：如果 DB 中的数量大于 N，返回 JSON 会携带一个字段“cursor”,我们把它叫做”游标”，
     * 该游标可理解为结果集的指针，值是变化的。往下取数据的时候带着游标，就可以获取到下一页的值。
     * 如果还有下一页，返回值里依然还有这个字段，直到没有这个字段，说明已经到最后一页。cursor的意义在于数据（真）分页。
     *
     * @param limit   要获取的用户数量
     * @param cursor  游标
     * @return User
     */
    public User batchGetUser(int limit, String cursor) {
        verifyLimit(limit);

        String uri;
        // 第一次获取不需要传cursor
        if (cursor != null) {
            verifyCursor(cursor);
            uri = "/users?limit=" + limit + "&cursor=" + cursor;
        } else {
            uri = "/users?limit=" + limit;
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.batchGetUser, null, response);
    }

    /**
     * 删除单个用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E5%88%A0%E9%99%A4%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7
     *
     * 删除一个用户，如果此用户时群组或者聊天室的群主owner，系统会同时删除这些群组和聊天室。请在操作时进行确认。
     *
     * @param username 用户的username
     * @return User
     */
    public User deleteUser(String username) {
        verifyUsername(username);
        String uri = "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.deleteUser, username, response);
    }

    /**
     * 批量删除用户
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7
     *
     * 删除某个 APP 下指定数量的环信账号。可一次删除 N 个用户，数值可以修改。建议这个数值在100-500之间，不要过大。
     * 需要注意的是，这里只是批量的一次性删除掉 N个用户，具体删除哪些并没有指定，可以在返回值中查看到哪些用户被删除掉了。
     *
     * @param limit  要删除的用户数量
     * @param cursor 游标
     * @return User
     */
    public User batchDeleteUser(int limit, String cursor) {
        verifyLimit(limit);

        String uri;
        // 第一次删除不需要传cursor
        if (cursor != null) {
            verifyCursor(cursor);
            uri = "/users?limit=" + limit + "&cursor=" + cursor;
        } else {
            uri = "/users?limit=" + limit;
        }

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.batchDeleteUser, null, response);
    }

    /**
     * 修改用户密码
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E4%BF%AE%E6%94%B9%E7%94%A8%E6%88%B7%E5%AF%86%E7%A0%81
     *
     * 可以通过服务端接口修改 IM 用户的登录密码，不需要提供原密码
     *
     * @param username     用户的username
     * @param newPassword  新密码
     * @return Map<String, Object>
     */
    public Map<String, Object> modifyUserPassword(String username, String newPassword) {
        verifyUsername(username);
        verifyPassword(newPassword);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("newpassword", newPassword);

        String uri = "/users/" + username + "/password";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);

        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("action", response.get("action").asText());
        result.put("newPassword", newPassword);
        result.put("timestamp", response.get("timestamp").asLong());
        return result;
    }

    /**
     * 设置推送昵称
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E6%8E%A8%E9%80%81%E6%98%B5%E7%A7%B0
     *
     * 设置用户的推送昵称，在离线推送时使用
     *
     * @param username  用户的username
     * @param nickname  用户的推送昵称
     * @return User
     */
    public User setUserPushNickname(String username, String nickname) {
        verifyUsername(username);
        verifyNickname(nickname);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("nickname", nickname);

        String uri = "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.setUserPushNickname, username, response);
    }

    /**
     * 设置推送消息展示方式
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E6%8E%A8%E9%80%81%E6%B6%88%E6%81%AF%E5%B1%95%E7%A4%BA%E6%96%B9%E5%BC%8F
     *
     * 设置推送消息至客户端的方式，修改后及时有效。服务端对应不同的设置，向用户发送不同展示方式的消息。
     *
     * @param username      用户的username
     * @param displayStyle  客户端手机通知栏展示消息的样式 "0"仅通知(默认是"您有一条新消息")，"1"通知以及消息详情(展示消息内容)
     * @return User
     */
    public User setNotificationDisplayStyle(String username, int displayStyle) {
        verifyUsername(username);
        verifyDisplayStyle(String.valueOf(displayStyle));

        ObjectNode request = this.mapper.createObjectNode();
        request.put("notification_display_style", String.valueOf(displayStyle));

        String uri = "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.setNotificationDisplayStyle, username, response);
    }

    /**
     * 设置免打扰
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E5%85%8D%E6%89%93%E6%89%B0
     *
     * 设置 IM 用户免打扰，在免打扰期间，用户将不会收到离线消息推送
     *
     * @param username      用户的username
     * @param start         免打扰起始时间，单位是小时
     * @param end           免打扰结束时间，单位是小时
     * @return User
     */
    public User setNotificationNoDisturbing(String username, int start, int end) {
        verifyNickname(username);
        verifyNoDisturbingStart(String.valueOf(start));
        verifyNoDisturbingEnd(String.valueOf(end));

        ObjectNode request = this.mapper.createObjectNode();
        request.put("notification_no_disturbing", true);
        request.put("notification_no_disturbing_start", String.valueOf(start));
        request.put("notification_no_disturbing_end", String.valueOf(end));

        String uri = "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.setNotificationNoDisturbing, username, response);
    }

    /**
     * 取消免打扰
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E5%85%8D%E6%89%93%E6%89%B0
     *
     * @param username  用户的username
     * @return User
     */
    public User cancelNotificationNoDisturbing(String username) {
        verifyUsername(username);

        ObjectNode request = this.mapper.createObjectNode();
        request.put("notification_no_disturbing", false);

        String uri = "/users/" + username;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.PUT, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.cancelNotificationNoDisturbing, username, response);
    }

    /**
     * 添加好友
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%B7%BB%E5%8A%A0%E5%A5%BD%E5%8F%8B
     *
     * 添加好友，好友必须是和自己在一个 APPkey 下的 IM 用户
     *
     * @param ownerUsername   要添加好友的用户名
     * @param friendUsername  好友用户名
     * @return User
     */
    public User addContact(String ownerUsername, String friendUsername) {
        verifyUsername(ownerUsername);
        verifyUsername(friendUsername);

        String uri = "/users/" + ownerUsername + "/contacts/users/" + friendUsername;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.addContact, ownerUsername, response);
    }

    /**
     * 移除好友
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E7%A7%BB%E9%99%A4%E5%A5%BD%E5%8F%8B
     *
     * 从 IM 用户的好友列表中移除一个 IM 用户
     *
     * @param ownerUsername   要添加好友的用户名
     * @param friendUsername  好友用户名
     * @return User
     */
    public User removeContact(String ownerUsername, String friendUsername) {
        verifyUsername(ownerUsername);
        verifyUsername(friendUsername);

        String uri = "/users/" + ownerUsername + "/contacts/users/" + friendUsername;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.removeContact, ownerUsername, response);
    }

    /**
     * 获取好友列表
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E5%A5%BD%E5%8F%8B%E5%88%97%E8%A1%A8
     *
     * 获取 IM 用户的好友列表
     *
     * @param ownerUsername  获取好友列表的用户名
     * @return List<String>
     */
    public List<String> getContactList(String ownerUsername) {
        verifyUsername(ownerUsername);
        String uri = "/users/" + ownerUsername + "/contacts/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return jsonDataToArrayList(response);
    }

    /**
     * 获取黑名单
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E9%BB%91%E5%90%8D%E5%8D%95
     *
     * 获取 IM 用户的黑名单
     *
     * @param ownerUsername  获取黑名单的用户名
     * @return List<String>
     */
    public List<String> getBlockList(String ownerUsername) {
        verifyUsername(ownerUsername);
        String uri = "/users/" + ownerUsername + "/blocks/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return jsonDataToArrayList(response);
    }

    /**
     * 添加黑名单
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%B7%BB%E5%8A%A0%E9%BB%91%E5%90%8D%E5%8D%95
     *
     * 向 IM 用户的黑名单列表中添加一个或者多个用户，黑名单中的用户无法给该 IM 用户发送消息
     *
     * @param ownerUsername  要添加黑名单的用户名
     * @return List<String>
     */
    public List<String> addBlock(String ownerUsername, Set<String> usernames) {
        verifyUsername(ownerUsername);

        if (usernames == null || usernames.size() < 1) {
            throw new UserException("invalid usernames");
        }

        ArrayNode usernameArray = this.mapper.createArrayNode();
        for (String username : usernames) {
            verifyUsername(username);
            usernameArray.addPOJO(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", usernameArray);

        String uri = "/users/" + ownerUsername + "/blocks/users";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);

        return jsonDataToArrayList(response);
    }

    /**
     * 移除黑名单
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E7%A7%BB%E9%99%A4%E9%BB%91%E5%90%8D%E5%8D%95
     *
     * 从 IM 用户的黑名单中移除用户。将用户从黑名单移除后，恢复到好友，或者未添加好友的用户关系。可以正常的进行消息收发
     *
     * @param ownerUsername  要移除黑名单的用户名
     * @param blockUsername  黑名单中的用户名
     * @return User
     */
    public User removeBlock(String ownerUsername, String blockUsername) {
        verifyUsername(ownerUsername);
        verifyUsername(blockUsername);

        String uri = "/users/" + ownerUsername + "/blocks/users/" + blockUsername;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.DELETE, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.removeBlock, ownerUsername, response);
    }

    /**
     * 获取用户在线状态
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81
     *
     * 查看一个用户的在线状态，“offline”代表离线，“online”在表用户当前在线
     *
     * @param username  要获取在线状态的用户名
     * @return String  -> offline 或者 online
     */
    public String getUserStatus(String username) {
        verifyUsername(username);
        String uri = "/users/" + username + "/status";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return jsonDataToString(username, response);
    }

    /**
     * 批量获取用户在线状态
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81
     *
     * 批量查看用户的在线状态，最大同时查看100个用户
     *
     * @param usernames  需要查询状态的用户名以数组方式提交，最多不能超过100个
     * @return  "offline"代表离线，"online"在表用户当前在线
     */
    public List<Map<String, String>> batchGetUserStatus(Set<String> usernames) {
        if (usernames == null || usernames.size() < 1 || usernames.size() > 100) {
            throw new UserException("Bad Request invalid usernames");
        }

        for (String username : usernames) {
            verifyUsername(username);
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.set("usernames", this.mapper.valueToTree(usernames));

        String uri = "/users/batch/status";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, request, this.allocator, this.mapper, this.properties, this.tokenCache);
        return jsonDataToArrayList(response);
    }

    /**
     * 获取用户离线消息数
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E7%A6%BB%E7%BA%BF%E6%B6%88%E6%81%AF%E6%95%B0
     *
     * 获取 IM 用户的离线消息数量。默认的离线消息数量上限为1200条
     *
     * @param username  要获取离线消息数的用户名
     * @return int
     */
    public int getUserOfflineMessageCount(String username) {
        verifyUsername(username);

        String uri = "/users/" + username + "/offline_msg_count";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);

        JsonNode data = response.get("data");
        int offlineMsgCount;
        if (data != null) {
            if (data.get(username) != null) {
                offlineMsgCount = data.get(username).asInt();
            } else {
                throw new UserException("data username is null");
            }
        } else {
            throw new UserException("data is null");
        }
        return offlineMsgCount;
    }

    /**
     * 获取某条离线消息状态
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E6%9F%90%E6%9D%A1%E7%A6%BB%E7%BA%BF%E6%B6%88%E6%81%AF%E7%8A%B6%E6%80%81
     *
     * 获取 IM 用户的离线消息状态，查看用户的离线消息离线消息的状态
     * “delivered”表示状态为消息已投递，“undelivered”表示消息未投递
     *
     * @param username   要获取离线消息状态的用户名
     * @param messageId  要查看离线消息状态的消息ID
     * @return String  -> delivered 或 undelivered
     */
    public String getOfflineMessageStatus(String username, String messageId) {
        verifyUsername(username);
        verifyMessageId(messageId);

        String uri = "/users/" + username + "/offline_msg_status/" + messageId;
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);
        return jsonDataToString(messageId, response);
    }

    /**
     * 用户账号禁用
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E7%94%A8%E6%88%B7%E8%B4%A6%E5%8F%B7%E7%A6%81%E7%94%A8
     *
     * 禁用某个 IM 用户的账号，禁用后该用户不可登录，下次解禁后该账户恢复正常使用
     *
     * @param username  要禁用的用户名
     * @return User
     */
    public User deactivateUser(String username) {
        verifyUsername(username);
        String uri = "/users/" + username + "/deactivate";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);
        return responseToUserObject(OperationUserEvent.deactivateUser, username, response);
    }

    /**
     * 用户账号解禁
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E7%94%A8%E6%88%B7%E8%B4%A6%E5%8F%B7%E8%A7%A3%E7%A6%81
     *
     * 解除 IM 用户账号的禁用操作，由禁用到解禁操作后，需要用户重新登录
     *
     * @param username  要解禁的用户名
     * @return Map<String, Object>
     */
    public Map<String, Object> activateUser(String username) {
        verifyUsername(username);

        String uri = "/users/" + username + "/activate";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, uri, this.mapper, this.properties, this.tokenCache);

        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("action", response.get("action").asText());
        result.put("timestamp", response.get("timestamp").asLong());
        return result;
    }

    /**
     * 强制下线
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E5%BC%BA%E5%88%B6%E4%B8%8B%E7%BA%BF
     *
     * 强制 IM 用户状态改为离线，用户需要重新登录才能正常使用
     * “true”表示用户已经被强制下线， “false”表示用户没有被强制下线
     *
     * @param username  要强制下线的用户名
     * @return boolean  -> true 或 false
     */
    public boolean disconnect(String username) {
        verifyUsername(username);

        String uri = "/users/" + username + "/disconnect";
        JsonNode response = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper, this.properties, this.tokenCache);

        JsonNode data = response.get("data");
        boolean isDisconnect;
        if (data != null) {
            if (data.get("result") != null) {
                isDisconnect = data.get("result").asBoolean();
            } else {
                throw new UserException("data result is null");
            }
        } else {
            throw new UserException("data is null");
        }
        return isDisconnect;
    }

    // 验证 username
    private void verifyUsername(String username) {
        if (username == null || !VALID_USERNAME_PATTERN.matcher(username).matches()) {
            throw new UserException(String.format("Bad Request %s invalid username", username));
        }
    }

    // 验证 password
    private void verifyPassword(String password) {
        if (password == null || !VALID_PASSWORD_PATTERN.matcher(password).matches()) {
            throw new UserException(String.format("Bad Request %s invalid password", password));
        }
    }

    // 验证 nickname
    private void verifyNickname(String nickname) {
        if (!VALID_NICKNAME_PATTERN.matcher(nickname).matches()) {
            throw new UserException(String.format("Bad Request %s invalid nickname", nickname));
        }
    }

    // 验证 limit
    private void verifyLimit(int limit) {
        if (limit < 1) {
            throw new UserException("Bad Request invalid limit");
        }
    }

    // 验证 cursor
    private void verifyCursor(String cursor) {
        if (cursor == null || cursor.isEmpty()) {
            throw new UserException("Bad Request invalid cursor");
        }
    }

    // 验证 notification display style
    private void verifyDisplayStyle(String style) {
        if (style == null || !VALID_DISPLAY_STYLE_PATTERN.matcher(style).matches()) {
            throw new UserException(String.format("Bad Request %s invalid style", style));
        }
    }

    // 验证 no disturbing start
    private void verifyNoDisturbingStart(String start) {
        if (start == null || !VALID_NO_DISTURBING_START_PATTERN.matcher(start).matches()) {
            throw new UserException(String.format("Bad Request %s invalid start time", start));
        }
    }

    // 验证 no disturbing end
    private void verifyNoDisturbingEnd(String end) {
        if (end == null || !VALID_NO_DISTURBING_END_PATTERN.matcher(end).matches()) {
            throw new UserException(String.format("Bad Request %s invalid end time", end));
        }
    }

    // 验证 message id
    private void verifyMessageId(String messageId) {
        if (messageId == null || !VALID_MESSAGE_ID_PATTERN.matcher(messageId).matches()) {
            throw new UserException("Bad Request invalid messageId");
        }
    }

    private <T> List<T> jsonDataToArrayList(JsonNode response) {
        List<T> resultList;
        if (response.get("data") != null) {
            try {
                resultList = mapper.treeToValue(response.get("data"), List.class);
            } catch (JsonProcessingException e) {
                throw new UserException("data to list fail ", e);
            }
        } else {
            throw new UserException("data is null ");
        }
        return resultList;
    }

    private String jsonDataToString(String name, JsonNode response) {
        JsonNode data = response.get("data");
        String status;
        if (data != null) {
            if (data.get(name) != null) {
                status = data.get(name).asText();
            } else {
                throw new UserException(String.format("data %s is null", name));
            }
        } else {
            throw new UserException("data is null");
        }
        return status;
    }

    // 操作用户的返回结果转成 User 对象
    private User responseToUserObject(OperationUserEvent event, String username, JsonNode response) {
        ArrayNode entities = (ArrayNode) response.get("entities");
        if (entities == null || entities.size() < 1) {
            throw new UserException("entities is null");
        }

        List<Map<String, Object>> entitiesList;
        try {
            entitiesList = this.mapper.treeToValue(entities, List.class);
        } catch (JsonProcessingException e) {
            throw new UserException("entities to ArrayList fail " + e);
        }

        Long timestamp;
        if (response.get("timestamp") != null) {
            timestamp = response.get("timestamp").asLong();
        } else {
            timestamp = null;
        }

        if (event == OperationUserEvent.batchGetUser || event == OperationUserEvent.batchDeleteUser) {
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
            return User.builder()
                    .event(event)
                    .username(username)
                    .entities(entitiesList)
                    .cursor(cursor)
                    .count(count)
                    .timeStamp(timestamp)
                    .build();
        }

        return User.builder()
                .event(event)
                .username(username)
                .entities(entitiesList)
                .timeStamp(timestamp)
                .build();
    }

}
