package com.easemob.im.server.api.chatrooms.admin.promote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PromoteRoomAdminRequest {
    @JsonProperty("newadmin")
    private String username;

    @JsonCreator
    public PromoteRoomAdminRequest(@JsonProperty("newadmin") String username) {
        this.username = username;
    }

}
