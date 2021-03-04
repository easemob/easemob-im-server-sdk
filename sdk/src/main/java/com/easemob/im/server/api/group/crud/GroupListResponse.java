package com.easemob.im.server.api.group.crud;

import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupListResponse {

    @JsonProperty("data")
    private List<GroupResource> groups;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public GroupListResponse(@JsonProperty("data") List<GroupResource> groups,
                             @JsonProperty("cursor") String cursor) {
        this.groups = groups;
        this.cursor = cursor;
    }

    public List<EMGroup> getEMGroups() {
        return groups.stream().map(GroupResource::toGroup).collect(Collectors.toList());
    }

    public String getCursor() {
        return cursor;
    }

    @Override
    public String toString() {
        return "GroupListResponse{" +
            "groups=" + groups +
            ", cursor='" + cursor + '\'' +
            '}';
    }

    private static class GroupResource {
        @JsonProperty("groupid")
        private String groupId;

        @JsonCreator
        public GroupResource(@JsonProperty("groupid") String groupId) {
            this.groupId = groupId;
        }

        public EMGroup toGroup() {
            return new EMGroup(this.groupId);
        }

        @Override
        public String toString() {
            return "GroupResource{" +
                "groupId='" + groupId + '\'' +
                '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof GroupResource)) {
                return false;
            }
            GroupResource that = (GroupResource) o;
            return Objects.equals(groupId, that.groupId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(groupId);
        }
    }
}
