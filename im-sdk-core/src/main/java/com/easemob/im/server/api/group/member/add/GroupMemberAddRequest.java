package com.easemob.im.server.api.group.member.add;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupMemberAddRequest {
    @JsonProperty("usernames")
    private List<String> members;

    @JsonCreator
    public GroupMemberAddRequest(@JsonProperty("usernames") List<String> members) {
        this.members = members;
    }
}
