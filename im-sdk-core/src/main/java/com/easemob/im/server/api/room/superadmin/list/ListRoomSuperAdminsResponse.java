package com.easemob.im.server.api.room.superadmin.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListRoomSuperAdminsResponse {
    @JsonProperty("data")
    private List<String> admins;

    @JsonCreator
    public ListRoomSuperAdminsResponse(@JsonProperty("data") List<String> admins) {
        this.admins = admins;
    }

    public List<String> getAdmins() {
        return admins;
    }
}
