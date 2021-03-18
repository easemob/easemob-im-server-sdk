package com.easemob.im.server.api.room.superadmin.demote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DemoteRoomSuperAdminResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("newSuperAdmin")
        private String username;
        @JsonProperty("resource")
        private String resource;

        @JsonCreator
        public Wrapper(@JsonProperty("newSuperAdmin") String username,
                       @JsonProperty("resource") String resource) {
            this.username = username;
            this.resource = resource;
        }
    }

    @JsonCreator
    public DemoteRoomSuperAdminResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean isSucess() {
        return this.wrapper != null;
    }
}
