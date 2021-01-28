package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.user.UserResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserUnregisterResponse {
    @JsonProperty("entities")
    private List<UserResource> entities;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public UserUnregisterResponse(@JsonProperty("entities") List<UserResource> entities,
                                  @JsonProperty("cursor") String cursor) {
        this.entities = entities;
        this.cursor = cursor;
    }

    public List<UserResource> getEntities() {
        return this.entities;
    }

    public String getCursor() {
        return this.cursor;
    }

}
