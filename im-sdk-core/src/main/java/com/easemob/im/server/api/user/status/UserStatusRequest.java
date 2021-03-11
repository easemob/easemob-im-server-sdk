package com.easemob.im.server.api.user.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserStatusRequest {
    @JsonProperty("usernames")
    private List<String> usernames;

    @JsonCreator
    public UserStatusRequest(@JsonProperty("usernames") List<String> usernames) {
        this.usernames = usernames;
    }
}
