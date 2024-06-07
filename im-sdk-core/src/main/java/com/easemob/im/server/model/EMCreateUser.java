package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EMCreateUser {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("nickname")
    private String pushNickname;

    @JsonCreator
    public EMCreateUser(@JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    @JsonCreator
    public EMCreateUser(@JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("nickname") String pushNickname) {
        this.username = username;
        this.password = password;
        this.pushNickname = pushNickname;
    }
}
