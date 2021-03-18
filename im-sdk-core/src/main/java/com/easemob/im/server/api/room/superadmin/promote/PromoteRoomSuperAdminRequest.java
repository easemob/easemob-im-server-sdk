package com.easemob.im.server.api.room.superadmin.promote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PromoteRoomSuperAdminRequest {
    @JsonProperty("superadmin")
    private String username;

    @JsonCreator
    public PromoteRoomSuperAdminRequest(@JsonProperty("superadmin") String username) {
        this.username = username;
    }
}
