package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EMPushNickname {

    @JsonProperty("username")
    private String username;

    @JsonProperty("push_nickname")
    private String pushNickname;

    @JsonCreator
    public EMPushNickname(@JsonProperty("username") String username,
            @JsonProperty("push_nickname") String pushNickname) {
        this.username = username;
        this.pushNickname = pushNickname;
    }
}
