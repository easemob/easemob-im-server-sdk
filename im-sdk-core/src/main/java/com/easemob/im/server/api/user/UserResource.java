package com.easemob.im.server.api.user;

import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource {

    @JsonProperty("username")
    private String username;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("activated")
    private boolean activated;

    @JsonCreator
    public UserResource(
            @JsonProperty("username") String username,
            @JsonProperty("uuid") String uuid,
            @JsonProperty("activated") boolean activated) {
        this.username = username;
        this.uuid = uuid;
        this.activated = activated;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public boolean isActivated() {
        return activated;
    }

    public EMUser toEMUser() {
        return new EMUser(this.username, this.uuid, this.activated);
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                ", activated=" + activated +
                '}';
    }
}
