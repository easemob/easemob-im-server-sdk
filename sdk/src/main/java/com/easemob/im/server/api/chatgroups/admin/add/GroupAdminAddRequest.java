package com.easemob.im.server.api.chatgroups.admin.add;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupAdminAddRequest {
    @JsonProperty("newadmin")
    private String username;

    @JsonCreator
    public GroupAdminAddRequest(@JsonProperty("newadmin") String username) {
        this.username = username;
    }

}
