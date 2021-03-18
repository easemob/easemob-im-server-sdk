package com.easemob.im.server.api.user.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserResponse {
    @JsonProperty("error")
    private String error;

    @JsonCreator
    public CreateUserResponse(@JsonProperty("error") String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
