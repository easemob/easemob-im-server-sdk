package com.easemob.im.server.api.group.member.list;

import com.easemob.im.server.api.group.GroupMemberResource;
import com.easemob.im.server.model.EMPage;
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

    public EMPage<String> toEMPage() {
        List<String> usernames = this.members.stream()
                .map(GroupMemberResource::getUsername)
                .collect(Collectors.toList());
        return new EMPage<>(usernames, this.cursor);
    }

    public String getCursor() {
        return this.cursor;
    }
}
