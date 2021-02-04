package com.easemob.im.server.api.user.status;

import com.easemob.im.server.model.EMUserStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserStatusResponse {
    @JsonProperty("data")
    private Map<String, String> statusByUsername;

    @JsonCreator
    public UserStatusResponse(@JsonProperty("data") Map<String, String> statusByUsername) {
        this.statusByUsername = statusByUsername;
    }

    public List<EMUserStatus> getUserStatusList() {
        return this.statusByUsername.entrySet()
            .stream()
            .map(e -> {
                boolean isOnline = e.getValue().equals("online");
                return new EMUserStatus(e.getKey(), isOnline);
            })
            .collect(Collectors.toList());
    }

}
