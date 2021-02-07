package com.easemob.im.server.api.chatgroups.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupAdminListResponse {
    @JsonProperty("data")
    private List<String> admins;

    @JsonCreator
    public GroupAdminListResponse(@JsonProperty("data") List<String> admins) {
        this.admins = admins;
    }

    public List<String> getAdmins() {
        return admins;
    }

}
