package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.get.UserGet;
import com.easemob.im.server.api.user.register.UserRegister;
import com.easemob.im.server.api.user.unregister.UserUnregister;

public class UserApiGroupV1 {

    private UserGet get;

    private UserRegister register;

    private UserUnregister unregister;

    public UserApiGroupV1(Context context) {
        this.get = new UserGet(context);
        this.register = new UserRegister(context);
        this.unregister = new UserUnregister(context);
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
}
