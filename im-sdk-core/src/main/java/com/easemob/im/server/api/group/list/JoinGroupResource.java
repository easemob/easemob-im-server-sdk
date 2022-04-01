package com.easemob.im.server.api.group.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class JoinGroupResource {
    @JsonProperty("groupid")
    private String groupId;

    @JsonProperty("groupname")
    private String groupName;

    @JsonCreator
    public JoinGroupResource(@JsonProperty("groupid") String groupId, @JsonProperty("groupname") String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    @Override public String toString() {
        return "GroupResource{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JoinGroupResource)) {
            return false;
        }
        JoinGroupResource that = (JoinGroupResource) o;
        return Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
}
