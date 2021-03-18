package com.easemob.im.server.api.user;

import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource {

    @JsonProperty("username")
    private String username;

    @JsonProperty("activated")
    private boolean activated;

    @JsonCreator
    public UserResource(@JsonProperty("username") String username,
                        @JsonProperty("activated") boolean activated) {
        this.username = username;
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public boolean isActivated() {
        return activated;
    }

    public EMUser toEMUser() {
        return new EMUser(this.username, this.activated);
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "username='" + username + '\'' +
                ", activated=" + activated +
                '}';
    }
}
