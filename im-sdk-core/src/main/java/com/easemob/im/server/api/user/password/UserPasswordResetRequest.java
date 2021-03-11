package com.easemob.im.server.api.user.password;

import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPasswordResetRequest {
    @JsonProperty("newpassword")
    private String password;

    @JsonCreator
    public UserPasswordResetRequest(@JsonProperty("newpassword") String password) {
        EMUser.validatePassword(password);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
