package com.easemob.im.server.api.user.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("nickname")
    private String pushNickname;

    @JsonCreator
    public CreateUserRequest(@JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("nickname") String pushNickname) {
        this.username = username;
        this.password = password;
        this.pushNickname = pushNickname;
    }

    public String getUsername() {
        return this.username;
    }

}
