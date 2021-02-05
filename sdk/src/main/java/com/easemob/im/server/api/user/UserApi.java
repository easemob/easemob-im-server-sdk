package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.forcelogout.UserForceLogout;
import com.easemob.im.server.api.user.get.UserGet;
import com.easemob.im.server.api.user.password.UserPassword;
import com.easemob.im.server.api.user.register.UserRegister;
import com.easemob.im.server.api.user.register.UserRegisterRequest;
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
    public Mono<EMUser> register(String username, String password) {
        return UserRegister.single(this.context, username, password);
    }

    /**
     * Delete all users.
     *
     * @param limit how many users to delete each time
     * @return A {@code Flux} of deleted users.
     */
    public Flux<EMUser> deleteAll(int limit) {
        return UserUnregister.all(this.context, limit);
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
     * Delete a user.
     *
     * @param username the username
     * @return A {@code Mono} emits deleted {@code EMUser} on success.
     */
    public Mono<EMUser> delete(String username) {
        return UserUnregister.single(this.context, username);
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
