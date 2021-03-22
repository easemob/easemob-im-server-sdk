package com.easemob.im.server.api.group.get;

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

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

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

        public EMGroup toEMGroup() {
            return new EMGroup(this.groupId, this.name, this.description, this.isPublic, this.needApproveToJoin, this.memberCanInviteOthers,
                    this.owner, this.maxMembers);
        }
    }

}
