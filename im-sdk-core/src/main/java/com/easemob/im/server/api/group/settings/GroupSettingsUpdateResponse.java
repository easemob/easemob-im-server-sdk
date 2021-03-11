package com.easemob.im.server.api.group.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupSettingsUpdateResponse {

    @JsonProperty("data")
    private GroupUpdateResource resource;

    @JsonCreator
    public GroupSettingsUpdateResponse(@JsonProperty("data") GroupUpdateResource resource) {
        this.resource = resource;
    }

    private static class GroupUpdateResource {
        @JsonProperty("maxusers")
        private Boolean maxMembersUpdated;
        @JsonProperty("membersonly")
        private Boolean needApproveToJoinUpdated;
        @JsonProperty("allowinvites")
        private Boolean memberCanInviteOthersUpdated;

        @JsonCreator
        public GroupUpdateResource(@JsonProperty("maxusers") Boolean maxMembersUpdated,
                                   @JsonProperty("membersonly") Boolean needApproveToJoinUpdated,
                                   @JsonProperty("allowinvites") Boolean memberCanInviteOthersUpdated) {
            this.maxMembersUpdated = maxMembersUpdated;
            this.needApproveToJoinUpdated = needApproveToJoinUpdated;
            this.memberCanInviteOthersUpdated = memberCanInviteOthersUpdated;
        }

        public Boolean getMaxMembersUpdated() {
            return maxMembersUpdated;
        }

        public Boolean getNeedApproveToJoinUpdated() {
            return needApproveToJoinUpdated;
        }

        public Boolean getMemberCanInviteOthersUpdated() {
            return memberCanInviteOthersUpdated;
        }
    }

    public Boolean getMaxMembersUpdated() {
        return this.resource.getMaxMembersUpdated();
    }

    public Boolean getNeedApproveToJoinUpdated() {
        return this.resource.getNeedApproveToJoinUpdated();
    }

    public Boolean getMemberCanInviteOthersUpdated() {
        return this.resource.getMemberCanInviteOthersUpdated();
    }
}
