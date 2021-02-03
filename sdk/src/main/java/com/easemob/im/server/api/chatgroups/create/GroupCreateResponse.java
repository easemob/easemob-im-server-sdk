package com.easemob.im.server.api.chatgroups.create;

import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupCreateResponse {

    @JsonProperty("data")
    private GroupCreateResource group;

    private static class GroupCreateResource {
        @JsonProperty("groupid")
        private String groupId;

        @JsonCreator
        public GroupCreateResource(@JsonProperty("groupid") String groupId) {
            this.groupId = groupId;
        }

        public String getGroupId() {
            return groupId;
        }

    }

    public EMGroup toEMGroup() {
        return new EMGroup(this.group.getGroupId());
    }
}
