package com.easemob.im.server.api.user.create;

import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMPage;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class BatchCreateUserResponse {
    @JsonProperty("entities")
    private List<UserResource> entities;

    @JsonCreator
    public BatchCreateUserResponse(@JsonProperty("entities") List<UserResource> entities) {
        this.entities = entities;
    }

    public List<EMUser> toEMUsers() {
        return this.entities.stream().map(UserResource::toEMUser).collect(Collectors.toList());
    }
}
