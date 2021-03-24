package com.easemob.im.server.api.user;

import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource {

    @JsonProperty("username")
    private String username;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("activated")
    private boolean activated;

    @JsonCreator
    public UserResource(@JsonProperty("username") String username,
                        @JsonProperty("nickname") String nickname,
                        @JsonProperty("activated") boolean activated) {
        this.username = username;
        this.nickname = nickname;
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isActivated() {
        return activated;
    }

    public EMUser toEMUser() {
        return new EMUser(this.username, this.nickname, this.activated);
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", activated=" + activated +
                '}';
    }
}
