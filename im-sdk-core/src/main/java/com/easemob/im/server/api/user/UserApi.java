package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.api.user.create.CreateUser;
import com.easemob.im.server.api.user.forcelogout.ForceLogoutUser;
import com.easemob.im.server.api.user.get.UserGet;
import com.easemob.im.server.api.user.list.ListUsers;
import com.easemob.im.server.api.user.password.UpdateUserPassword;
import com.easemob.im.server.api.user.status.UserStatus;
import com.easemob.im.server.api.user.unregister.DeleteUser;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMPage;
import com.easemob.im.server.model.EMUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
     *
     * @param username 用户名，可以包含小写字母、数字、减号，有效长度1至32个字节
     * @param password 密码，可以包含字母、数字、特殊符号(~!@#$%^&amp;*-_=+&lt;&gt;;:,./?)，有效长度1至32字节
     * @return A {@code Mono}
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%B3%A8%E5%86%8C%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7_%E6%8E%88%E6%9D%83">注册用户</a>
     */
    public Mono<Void> create(String username, String password) {
        try {
            EMUser.validateUsername(username);
            EMUser.validatePassword(password);
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }
        return this.createUser.single(username, password);
    }

    /**
     * 删除用户。
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
     *
     * @return 用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7">获取用户列表</a>
     */
    public Flux<String> listAllUsers() {
        return this.listUsers.all(20);
    }

    /**
     * 分页获取用户列表。
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
     *
     * @return 删除的每个用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%89%B9%E9%87%8F%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7">删除全部用户</a>
     */
    public Flux<String> deleteAll() {
        return this.deleteUser.all(20);
    }

    /**
     * 获取用户详情。
     *
     * @param username 用户名
     * @return A {@code Mono} emits {@code EMUser} on success.
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7">获取用户详情</a>
     */
    public Mono<EMUser> get(String username) {
        return this.userGet.single(username);
    }

    public Mono<String> getUUID(String username) {
        return this.get(username).map(EMUser::getUuid);
    }

    /**
     * 修改用户密码。
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
     *
     * @param username 要查询的用户的用户名
     * @return 是否在线或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81">获取用户在线状态</a>
     */
    public Mono<Boolean> isUserOnline(String username) {
        return this.userStatus.isUserOnline(username);
    }

    /**
     * 获取用户token。
     *
     * @param username 要获取token的用户名
     * @param password 要获取token的用户名密码
     * @return 返回token或失败
     */
    public Mono<Token> getToken(String username, String password) {
        return this.context.getTokenProvider().fetchUserToken(username, password);
    }

    // TODO: here we have 2 ways of getting an user token and we need a better design
    public Mono<Token> getToken (String userId, int expireSeconds,
            Consumer<AccessToken2> tokenConfigurer) throws Exception {
        return this.context.getTokenProvider()
                .buildUserToken(userId, expireSeconds, tokenConfigurer);
    }
}
