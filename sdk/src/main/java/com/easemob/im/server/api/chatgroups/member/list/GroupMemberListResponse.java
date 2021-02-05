package com.easemob.im.server.api.chatgroups.member.list;

import com.easemob.im.server.api.chatgroups.GroupMemberResource;
import com.easemob.im.server.model.EMGroupMember;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberListResponse {

    @JsonProperty("data")
    private List<GroupMemberResource> members;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public GroupMemberListResponse(@JsonProperty("data") List<GroupMemberResource> members,
                                   @JsonProperty("cursor") String cursor) {
        this.members = members;
        this.cursor = cursor;
    }

    public List<EMGroupMember> getMembers() {
        return this.members.stream().map(GroupMemberResource::toGroupMember).collect(Collectors.toList());
    }

    public String getCursor() {
        return this.cursor;
    }
}
