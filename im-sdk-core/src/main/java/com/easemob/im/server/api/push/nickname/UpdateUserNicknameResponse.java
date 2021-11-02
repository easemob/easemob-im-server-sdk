package com.easemob.im.server.api.push.nickname;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserNicknameResponse {

    @JsonProperty("error")
    private String error;

    @JsonCreator
    public UpdateUserNicknameResponse(@JsonProperty("error") String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
