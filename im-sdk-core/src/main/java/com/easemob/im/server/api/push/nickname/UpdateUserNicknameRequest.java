package com.easemob.im.server.api.push.nickname;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserNicknameRequest {

    @JsonProperty("nickname")
    private String nickname;

    @JsonCreator
    public UpdateUserNicknameRequest(@JsonProperty("nickname") String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
