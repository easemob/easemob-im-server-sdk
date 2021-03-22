package com.easemob.im.server.api.group.member.list;

import com.easemob.im.server.model.EMPage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//     "data": ["username1", "username2"]
public class GroupMemberListResponse {

    @JsonProperty("data")
    private List<String> members;

    @JsonProperty("cursor")
    private String cursor;

    public EMPage<String> toEMPage() {
        return new EMPage<>(this.members, this.cursor);
    }

    public String getCursor() {
        return this.cursor;
    }
}
