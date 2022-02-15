package com.easemob.im.server.api.group.get;

import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.util.internal.StringUtil;

import java.util.List;
import java.util.Optional;
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

        @JsonProperty("affiliations_count")
        private int affiliationsCount;

        @JsonProperty("affiliations")
        private List<Affiliation> affiliations;

        @JsonProperty("mute")
        private boolean isMute;

        @JsonProperty("custom")
        private String custom;

        public EMGroup toEMGroup() {
            String[] groupMembers = Optional.ofNullable(affiliations).map(list -> list.stream()
                    .filter(affiliation -> StringUtil.isNullOrEmpty(affiliation.getOwner()))
                    .map(Affiliation::getMember)
                    .toArray(String[]::new))
                    .orElse(null);
            return new EMGroup(this.groupId, this.name, this.description, this.isPublic,
                    this.needApproveToJoin, this.memberCanInviteOthers,
                    this.owner, this.maxMembers, this.affiliationsCount, groupMembers, this.isMute,
                    this.custom);
        }
    }

    private static class Affiliation {

        @JsonProperty("owner")
        private String owner;

        @JsonProperty("member")
        private String member;

        public String getOwner() {
            return owner;
        }

        public String getMember() {
            return member;
        }
    }

}
