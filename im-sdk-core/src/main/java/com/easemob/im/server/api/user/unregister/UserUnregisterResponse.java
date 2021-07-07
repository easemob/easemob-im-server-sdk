package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class UserUnregisterResponse {
    @JsonProperty("entities")
    private List<UserResource> entities;

    @JsonProperty("cursor")
    private String cursor;

    @JsonProperty("error")
    private String error;

    @JsonCreator
    public UserUnregisterResponse(@JsonProperty("entities") List<UserResource> entities,
            @JsonProperty("cursor") String cursor,
            @JsonProperty("error") String error) {
        this.entities = entities;
        this.cursor = cursor;
        this.error = error;
    }

    public List<String> getUsernames() {
        return this.entities.stream()
                .map(UserResource::getUsername)
                .collect(Collectors.toList());
    }

    public EMUser getEMUser(String username) {
        return this.entities.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(UserResource::toEMUser)
                .orElse(null);
    }

    public String getCursor() {
        return this.cursor;
    }

    public String getError() {
        return this.error;
    }
}
