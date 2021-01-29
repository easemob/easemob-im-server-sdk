package com.easemob.im.server.api.user.register;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegisterRequestV1 {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonCreator
    public UserRegisterRequestV1(@JsonProperty("username") String username,
                                 @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

}
