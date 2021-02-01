package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.forcelogout.UserForceLogout;
import com.easemob.im.server.api.user.get.UserGet;
import com.easemob.im.server.api.user.password.UserPassword;
import com.easemob.im.server.api.user.register.UserRegister;
import com.easemob.im.server.api.user.unregister.UserUnregister;

public class UserApiGroupV1 {

    private UserGet get;

    private UserRegister register;

    private UserUnregister unregister;

    private UserPassword password;

    private UserForceLogout forceLogout;

    public UserApiGroupV1(Context context) {
        this.get = new UserGet(context);
        this.register = new UserRegister(context);
        this.unregister = new UserUnregister(context);
        this.password = new UserPassword(context);
        this.forceLogout = new UserForceLogout(context);
    }

    public UserGet get() {
        return this.get;
    }

    public UserRegister register() {
        return this.register;
    }

    public UserUnregister unregister() {
        return this.unregister;
    }

    public UserPassword password() {
        return this.password;
    }

    public UserForceLogout forceLogout() {
        return this.forceLogout;
    }
}
