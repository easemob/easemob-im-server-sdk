package com.easemob.im.server.api.group.member.remove;

import com.easemob.im.server.model.EMRemoveMember;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupMemberRemoveResponse {
    @JsonProperty("data")
    private List<EMRemoveMember> members;

    @JsonCreator
    public GroupMemberRemoveResponse(
            @JsonProperty("data") List<EMRemoveMember> members) {
        this.members = members;
    }

    public List<EMRemoveMember> getMembers() {
        return members;
    }
}
