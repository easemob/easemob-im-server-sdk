package com.easemob.im.server.api.user.list;

import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class UserListResponse {

    @JsonProperty("entities")
    private List<UserResource> entities;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public UserListResponse(@JsonProperty("entities") List<UserResource> entities,
                            @JsonProperty("cursor") String cursor) {
        this.entities = entities;
        this.cursor = cursor;
    }

    public List<EMUser> getEMUsers() {
        return this.entities.stream().map(UserResource::toEMUser).collect(Collectors.toList());
    }

    public EMUser getEMUser(String username) {
        return this.entities.stream()
            .filter(user -> user.getUsername().equals(username)).findFirst()
            .map(UserResource::toEMUser).orElse(null);
    }

    public String getCursor() {
        return this.cursor;
    }

    @Override
    public String toString() {
        return "UserListResponse{" +
                "entities=" + getEMUsers().stream().map(EMUser::toString).collect(Collectors.joining(",")) +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
