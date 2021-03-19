package com.easemob.im.server.api.group.get;

import com.easemob.im.server.api.group.GroupMemberResource;
import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class GetGroupResponse {
    @JsonProperty("data")
    private List<GroupDetailResource> groupDetails;

    @JsonCreator
    public GetGroupResponse(@JsonProperty("data") List<GroupDetailResource> groupDetails) {
        this.groupDetails = groupDetails;
    }

    public EMGroup toGroupDetail(String groupId) {
        return this.groupDetails.stream()
            .filter(groupDetailResource -> groupDetailResource.groupId.equals(groupId))
            .map(GroupDetailResource::toEMGroup)
            .findFirst()
            .orElse(null);
    }

    public List<EMGroup> toGroupDetails() {
        return this.groupDetails.stream()
            .map(GroupDetailResource::toEMGroup)
            .collect(Collectors.toList());
    }

    private static class GroupDetailResource {
        @JsonProperty("id")
        private String groupId;

        @JsonProperty("membersonly")
        private boolean needApproveToJoin;

        @JsonProperty("allowinvites")
        private boolean memberCanInviteOthers; // only works in private group

        @JsonProperty("owner")
        private String owner;

        @JsonProperty("maxusers")
        private int maxMembers; // does owner count here?

        @JsonProperty("public")
        private boolean isPublic;
        // TODO: investigate how to avoid return members in get group detail api
        //      then, we can move group member resource into group member package
        @JsonProperty("affiliations")
        private List<GroupMemberResource> members;

        public EMGroup toEMGroup() {
            List<String> memberList = this.members.stream()
                .map(GroupMemberResource::getUsername)
                .collect(Collectors.toList());
            return new EMGroup(this.groupId, this.isPublic, this.needApproveToJoin, this.memberCanInviteOthers,
                    this.owner, this.maxMembers, memberList);
        }
    }

}
