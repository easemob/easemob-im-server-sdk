package com.easemob.im.server.api.group.member.remove;

import com.easemob.im.server.model.EMRemoveMember;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupMemberRemoveResponse {
    @JsonProperty("data")
    private Object members;

    @JsonCreator
    public GroupMemberRemoveResponse(
            @JsonProperty("data") Object members) {
        this.members = members;
    }

    public List<EMRemoveMember> getMembers() {
        List<EMRemoveMember> removeMembers = new ArrayList<>();

        if (members instanceof List) {
            ((List) members).forEach(member -> {
                removeMembers.add(toRemoveMember((Map<String, Object>) member));
            });
        } else {
            removeMembers.add(toRemoveMember((Map<String, Object>) members));
        }

        return removeMembers;
    }

    private EMRemoveMember toRemoveMember(Map<String, Object> member) {
        return new EMRemoveMember((Boolean) member.get("result"), String.valueOf(member.get("user")), String.valueOf(member.get("reason")));
    }
}
