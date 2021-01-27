package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;

public class UserApiGroupV1 {

    private UserRegister register;

    public UserApiGroupV1(Context context) {
        this.register = new UserRegister(context);
    }

    public UserRegister register() {
        return this.register;
    }
}
