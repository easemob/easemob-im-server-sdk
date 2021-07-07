package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserGetResponse {

    @JsonProperty("entities")
    private List<UserResource> entities;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public UserGetResponse(@JsonProperty("entities") List<UserResource> entities,
            @JsonProperty("cursor") String cursor) {
        this.entities = entities;
        this.cursor = cursor;
    }

    public EMUser getEMUser(String username) {
        return this.entities.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .map(UserResource::toEMUser).orElse(null);
    }

    public String getCursor() {
        return this.cursor;
    }

}
