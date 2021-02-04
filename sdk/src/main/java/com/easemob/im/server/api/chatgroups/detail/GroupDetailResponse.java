package com.easemob.im.server.api.chatgroups.detail;

import com.easemob.im.server.model.EMGroupDetails;
import com.easemob.im.server.model.EMGroupMember;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class GroupDetailResponse {
    @JsonProperty("data")
    private List<GroupDetailResource> groupDetails;

    @JsonCreator
    public GroupDetailResponse(@JsonProperty("data") List<GroupDetailResource> groupDetails) {
        this.groupDetails = groupDetails;
    }

    public EMGroupDetails toGroupDetail(String groupId) {
        return this.groupDetails.stream()
            .filter(groupDetailResource -> groupDetailResource.groupId.equals(groupId))
            .map(GroupDetailResource::toGroupDetail)
            .findFirst()
            .orElse(null);
    }

    public List<EMGroupDetails> toGroupDetails() {
        return this.groupDetails.stream()
            .map(GroupDetailResource::toGroupDetail)
            .collect(Collectors.toList());
    }

    private static class GroupDetailResource {
        @JsonProperty("id")
        private String groupId;

        @JsonProperty("membersonly")
        private boolean needApproveToJoin;

        @JsonProperty("allow")
        private boolean memberCanInviteOthers; // only works in private group

        @JsonProperty("maxusers")
        private int maxMembers; // does owner count here?

        @JsonProperty("public")
        private boolean isPublic;

        @JsonProperty("affiliations")
        private List<GroupMemberResource> members;

        public EMGroupDetails toGroupDetail() {
            List<EMGroupMember> memberList = this.members.stream()
                .map(GroupMemberResource::toGroupMember)
                .collect(Collectors.toList());
            return new EMGroupDetails(this.groupId, this.isPublic, this.needApproveToJoin, this.memberCanInviteOthers, this.maxMembers, memberList);
        }
    }

    public static class GroupMemberResource {
        @JsonProperty("member")
        private String memberUsername;
        @JsonProperty("owner")
        private String ownerUsername;

        @JsonCreator
        public GroupMemberResource(@JsonProperty("member") String memberUsername,
                                   @JsonProperty("owner") String ownerUsername) {
            this.memberUsername = memberUsername;
            this.ownerUsername = ownerUsername;
        }

        public EMGroupMember toGroupMember() {
            return this.memberUsername != null ? EMGroupMember.asMember(this.memberUsername) : EMGroupMember.asOwner(this.ownerUsername);
        }
    }

}
