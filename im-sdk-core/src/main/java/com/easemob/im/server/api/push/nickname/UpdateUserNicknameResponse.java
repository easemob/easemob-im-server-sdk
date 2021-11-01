package com.easemob.im.server.api.push.nickname;

import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UpdateUserNicknameResponse {

    @JsonProperty("entities")
    private List<UserResource> entities;


    @JsonCreator
    public UpdateUserNicknameResponse(@JsonProperty("entities") List<UserResource> entities) {
        this.entities = entities;
    }

    public EMUser getEMUser(String username) {
        return this.entities.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .map(UserResource::toEMUser).orElse(null);
    }
}
