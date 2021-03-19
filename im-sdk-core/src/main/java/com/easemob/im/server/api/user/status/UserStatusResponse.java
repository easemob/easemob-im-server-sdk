package com.easemob.im.server.api.user.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class UserStatusResponse {
    @JsonProperty("data")
    private Map<String, String> statusByUsername;

    @JsonCreator
    public UserStatusResponse(@JsonProperty("data") Map<String, String> statusByUsername) {
        this.statusByUsername = statusByUsername;
    }

    public Boolean isUserOnline(String username) {
        String status = this.statusByUsername.get(username);
        return status != null && status.equals("online");
    }

}
