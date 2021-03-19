package com.easemob.im.server.api.user.list;

import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMPage;
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

    public List<String> getUsernames() {
        return this.entities.stream().map(UserResource::getUsername).collect(Collectors.toList());
    }

    public EMUser getEMUser(String username) {
        return this.entities.stream()
            .filter(user -> user.getUsername().equals(username)).findFirst()
            .map(UserResource::toEMUser).orElse(null);
    }

    public EMPage<String> toEMPage() {
        List<String> usernames = this.entities.stream().map(UserResource::getUsername).collect(Collectors.toList());
        return new EMPage<>(usernames, this.cursor);
    }

    public String getCursor() {
        return this.cursor;
    }

    @Override
    public String toString() {
        return "UserListResponse{" +
                "entities=" + entities +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
