package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;

public class UserApiGroup {

    private UserRegister register;

    public UserApiGroup(Context context) {
        this.register = new UserRegister(context);
    }

    public UserRegister register() {
        return this.register;
    }
}
