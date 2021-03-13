package com.easemob.im.server.api.room.admin.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListRoomAdminsResponse {
    @JsonProperty("data")
    private List<String> admins;

    @JsonCreator
    public ListRoomAdminsResponse(@JsonProperty("data") List<String> admins) {
        this.admins = admins;
    }

    public List<String> getAdmins() {
        return this.admins;
    }
}
