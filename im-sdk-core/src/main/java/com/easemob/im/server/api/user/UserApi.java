package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.TokenApi;
import com.easemob.im.server.api.token.allocate.UserTokenRequest;
import com.easemob.im.server.api.user.create.CreateUser;
import com.easemob.im.server.api.user.forcelogout.ForceLogoutUser;
import com.easemob.im.server.api.user.get.UserGet;
import com.easemob.im.server.api.user.list.ListUsers;
import com.easemob.im.server.api.user.password.UpdateUserPassword;
import com.easemob.im.server.api.user.status.UserStatus;
import com.easemob.im.server.api.user.unregister.DeleteUser;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMCreateUser;
import com.easemob.im.server.model.EMPage;
import com.easemob.im.server.model.EMUser;
import com.easemob.im.server.model.EMUserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 用户API。
 */
public class UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApi.class);

    private CreateUser createUser;
    private DeleteUser deleteUser;
    private ListUsers listUsers;
    private UpdateUserPassword updateUserPassword;
    private ForceLogoutUser forceLogoutUser;

    private UserStatus userStatus;

    private UserGet userGet;

    private Context context;

    public UserApi(Context context) {
        this.createUser = new CreateUser(context);
        this.deleteUser = new DeleteUser(context);
        this.listUsers = new ListUsers(context);
        this.updateUserPassword = new UpdateUserPassword(context);
        this.forceLogoutUser = new ForceLogoutUser(context);
        this.userStatus = new UserStatus(context);
        this.userGet = new UserGet(context);
        this.context = context;
    }

    /**
     * 创建用户。
     * <p>
     * Server SDK 对创建的用户名有自己的限制，如果不想使用该限制，请查看此文档：
     * <a href="https://docs-im-beta.easemob.com/document/server-side/java_server_sdk.html#%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9">用户名限制</a>
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     EMUser user = service.user().create("username", "password").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名，可以包含小写字母、数字、减号，有效长度1至32个字节
     * @param password 密码，可以包含字母、数字、特殊符号(~!@#$%^&amp;*-_=+&lt;&gt;;:,./?)，有效长度1至32字节
     * @return EMUser
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%B3%A8%E5%86%8C%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7_%E6%8E%88%E6%9D%83">注册用户</a>
     */
    public Mono<EMUser> create(String username, String password) {
        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
            EMUser.validatePassword(password);
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }
        return this.createUser.single(username, password);
    }

    /**
     * 创建用户。
     * <p>
     * Server SDK 对创建的用户名有自己的限制，如果不想使用该限制，请查看此文档：
     * <a href="https://docs-im-beta.easemob.com/document/server-side/java_server_sdk.html#%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9">用户名限制</a>
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     EMUser user = service.user().create("username", "password", "pushNickname").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名，可以包含小写字母、数字、减号，有效长度1至32个字节
     * @param password 密码，可以包含字母、数字、特殊符号(~!@#$%^&amp;*-_=+&lt;&gt;;:,./?)，有效长度1至32字节
     * @param pushNickname 推送昵称，离线推送时在接收方的客户端推送通知栏中显示的发送方的昵称。你可以自定义该昵称，长度不能超过 100 个字符。
     *                     提示:1. 若不设置昵称，推送时会显示发送方的用户 ID，而非昵称。
     *                         2. 该昵称可与用户属性中的昵称设置不同，不过我们建议这两种昵称的设置保持一致。因此，修改其中一个昵称时，也需调用相应方法对另一个进行更新，确保设置一致。更新用户属性中的昵称的方法，详见 设置用户属性。
     * @return EMUser
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%B3%A8%E5%86%8C%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7_%E6%8E%88%E6%9D%83">注册用户</a>
     */
    public Mono<EMUser> create(String username, String password, String pushNickname) {
        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
            EMUser.validatePassword(password);
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }
        return this.createUser.single(username, password, pushNickname);
    }

    /**
     * 批量创建用户。
     * <p>
     * Server SDK 对创建的用户名有自己的限制，如果不想使用该限制，请查看此文档：
     * <a href="https://docs-im-beta.easemob.com/document/server-side/java_server_sdk.html#%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9">用户名限制</a>
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<EMCreateUser> createUsers = new ArrayList<>();
     *     EMCreateUser createUser1 = new EMCreateUser("user1", "123456");
     *     EMCreateUser createUser2 = new EMCreateUser("user2", "123456");
     *     createUsers.add(createUser1);
     *     createUsers.add(createUser2);
     *
     *     List<EMUser> users = service.user().create(createUsers).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param createUsers 需要创建用户的列表，EMCreateUser中包含用户名以及密码，用户名可以包含小写字母、数字、减号，有效长度1至32个字节
     *                    密码，可以包含字母、数字、特殊符号(~!@#$%^&amp;*-_=+&lt;&gt;;:,./?)，有效长度1至32字节
     * @return EMUser
     * @see <a href="https://docs-im-beta.easemob.com/document/server-side/account_system.html#%E6%89%B9%E9%87%8F%E6%B3%A8%E5%86%8C%E7%94%A8%E6%88%B7">批量注册用户</a>
     */
    public Mono<List<EMUser>> create(List<EMCreateUser> createUsers) {
        return this.createUser.batch(createUsers);
    }

    /**
     * 删除用户。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.user().delete("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要删除的用户的用户名
     * @return 成功或失败
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E5%88%A0%E9%99%A4%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7">删除用户</a>
     */
    public Mono<Void> delete(String username) {
        return this.deleteUser.single(username);
    }

    /**
     * 获取全部用户。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> users = service.user().listAllUsers().collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7">获取用户列表</a>
     */
    public Flux<String> listAllUsers() {
        return this.listUsers.all(20);
    }

    /**
     * 分页获取用户列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * EMPage<String> page = null;
     * try {
     *     page = service.user().listUsers(10, null).block();
     *     List<String> users = page.getValues();
     *     System.out.println("用户列表:" + users);
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * // ... do something with the users ...
     * if (page != null) {
     *      String cursor = page.getCursor();
     *      // cursor == null indicates the end of the list
     *      while (cursor != null) {
     *          try {
     *              page = service.user().listUsers(10, cursor).block();
     *              System.out.println("用户列表:" + page.getValues());
     *              // ... do something to the users ...
     *              cursor = page.getCursor();
     *          } catch (EMException e) {
     *              e.getErrorCode();
     *              e.getMessage();
     *          }
     *      }
     * }
     * }</pre>
     *
     * @param limit  返回多少用户
     * @param cursor 开始位置
     * @return 获取用户响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7">获取用户列表</a>
     * @see com.easemob.im.server.model.EMPage
     */
    public Mono<EMPage<String>> listUsers(int limit, String cursor) {
        return this.listUsers.next(limit, cursor);
    }

    /**
     * 删除全部用户。
     * <p>
     * 请谨慎使用。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> users = service.user().deleteAll().collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 删除的每个用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7">删除全部用户</a>
     */
    public Flux<String> deleteAll() {
        return this.deleteUser.all(20);
    }

    /**
     * 获取用户详情。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     EMUser user = service.user().get("username").block();
     *     String uuid = user.getUuid();
     *     String username = user.getUsername();
     *     Boolean canLogin = user.getCanLogin();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名
     * @return A {@code Mono} emits {@code EMUser} on success.
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7">获取用户详情</a>
     */
    public Mono<EMUser> get(String username) {
        return this.userGet.single(username);
    }

    /**
     * 修改用户密码。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.user().updateUserPassword("username", "password").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要修改的用户的用户名
     * @param password 新密码
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E4%BF%AE%E6%94%B9%E7%94%A8%E6%88%B7%E5%AF%86%E7%A0%81">修改用户密码</a>
     */
    public Mono<Void> updateUserPassword(String username, String password) {
        try {
            EMUser.validatePassword(password);
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }

        return this.updateUserPassword.update(username, password);
    }

    /**
     * 强制指定用户所有设备下线。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.user().forceLogoutAllDevices("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要强制下线的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E5%BC%BA%E5%88%B6%E4%B8%8B%E7%BA%BF">强制下线</a>
     */
    public Mono<Void> forceLogoutAllDevices(String username) {
        return this.forceLogoutUser.byUsername(username);
    }

    /**
     * 强制指定用户指定设备下线。
     * <p>
     * TODO: 增加查询用户在线设备id的API
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.user().forceLogoutOneDevice("username", "resource").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要强制下线的用户的用户名
     * @param resource 要强制下线的设备id，获取设备id的方法待补充
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E5%BC%BA%E5%88%B6%E4%B8%8B%E7%BA%BF">强制下线</a>
     */
    public Mono<Void> forceLogoutOneDevice(String username, String resource) {
        return this.forceLogoutUser.byUsernameAndResource(username, resource);
    }

    /**
     * 获取用户在线状态。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     Boolean isOnline = service.user().isUserOnline("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要查询的用户的用户名
     * @return 是否在线或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81">获取用户在线状态</a>
     */
    public Mono<Boolean> isUserOnline(String username) {
        return this.userStatus.isUserOnline(username);
    }

    /**
     * 批量获取用户在线状态
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> users = new ArrayList<>();
     * users.add("user1");
     * users.add("user2");
     *
     * try {
     *     List<EMUserStatus> userStatuses = service.user().isUsersOnline(users).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param usernames 需要查询状态的用户名
     * @return 是否在线或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81">批量获取用户在线状态</a>
     */
    public Mono<List<EMUserStatus>> isUsersOnline(List<String> usernames) {
        if (usernames == null || usernames.size() == 0) {
            return Mono.error(new EMInvalidArgumentException("usernames must not be null or empty"));
        }
        return this.userStatus.isUsersOnline(usernames);
    }

    /**
     * 获取用户token。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     Token token = service.user().getToken("u1", "123").block();
     *     String userToken = token.getValue();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要获取token的用户名
     * @param password 要获取token的用户名密码
     * @return 返回token或失败
     * @deprecated use {@link TokenApi#getUserToken(EMUser, Integer, Consumer, String)} instead.
     */
    @Deprecated
    public Mono<Token> getToken(String username, String password) {
        return TokenApi.fetchUserTokenWithEasemobRealm(this.context,
                UserTokenRequest.of(username, password));
    }
}
