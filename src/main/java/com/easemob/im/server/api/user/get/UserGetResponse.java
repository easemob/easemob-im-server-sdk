package com.easemob.im.server.api.user.get;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class UserGetResponse {

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

    public List<UserResource> getEntities() {
        return this.entities;
    }

    public String getCursor() {
        return this.cursor;
    }

    public static class UserResource {
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
    }

}
