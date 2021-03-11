package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.forcelogout.UserForceLogout;
import com.easemob.im.server.api.user.get.UserGet;
import com.easemob.im.server.api.user.list.UserList;
import com.easemob.im.server.api.user.list.UserListResponse;
import com.easemob.im.server.api.user.password.UserPassword;
import com.easemob.im.server.api.user.register.UserRegister;
import com.easemob.im.server.api.user.unregister.UserUnregister;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserApi {

    private Context context;

    public UserApi(Context context) {
        this.context = context;
    }


    /**
     * Register a user.
     *
     * @param username the username
     * @param password the password
     * @return A {@code Mono}
     */
    public Mono<EMUser> create(String username, String password) {
        return UserRegister.single(this.context, username, password);
    }

    /**
     * Delete a user.
     *
     * @param username the username
     * @return A {@code Mono} emits deleted {@code EMUser} on success.
     */
    public Mono<EMUser> delete(String username) {
        return UserUnregister.single(this.context, username);
    }

    /**
     * List all users.
     * Since this method will send requests recursively.
     *
     * @return A {@code Flux} which emits {@code EMUser}.
     */
    public Flux<EMUser> listAllUsers() {
        return UserList.all(this.context, 20);
    }

    /**
     * List limit users since cursor.
     *
     * @param limit the limit
     * @param cursor the cursor, from previous response
     * @return A {@code Mono} which emit {@code UserListResponse}.
     */
    public Mono<UserListResponse> listUsers(int limit, String cursor) {
        return UserList.next(this.context, limit, cursor);
    }

    /**
     * Delete all users.
     *
     * @return A {@code Flux} of deleted users.
     */
    public Flux<EMUser> deleteAll() {
        return UserUnregister.all(this.context, 10);
    }

    /**
     * Get a user.
     *
     * @param username the username
     * @return A {@code Mono} emits {@code EMUser} on success.
     */
    public Mono<EMUser> get(String username) {
        return UserGet.single(this.context, username);
    }



    /**
     * Reset user's password.
     *
     * @param username the username
     * @param password the password
     * @return A {@code Mono} completes on success.
     */
    public Mono<Void> resetPassword(String username, String password) {
        return UserPassword.reset(this.context, username, password);
    }

    /**
     * Force user logout.
     *
     * @param username the username
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> forceLogoutAllDevices(String username) {
        return UserForceLogout.byUsername(this.context, username);
    }

    /**
     * Force user logout.
     *
     * @param username the username
     * @param resource the device name
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> forceLogoutOneDevice(String username, String resource) {
        return UserForceLogout.byUsernameAndResource(this.context, username, resource);
    }


}
