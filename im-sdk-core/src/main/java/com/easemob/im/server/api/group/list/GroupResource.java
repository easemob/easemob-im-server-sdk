package com.easemob.im.server.api.group.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GroupResource {
    @JsonProperty("groupid")
    private String groupId;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("groupname")
    private String groupName;

    @JsonProperty("affiliations")
    private Integer affiliations;

    @JsonCreator
    public GroupResource(@JsonProperty("groupid") String groupId,
            @JsonProperty("groupname") String groupName,
            @JsonProperty("owner") String owner,
            @JsonProperty("affiliations") Integer affiliations) {
        this.groupId = groupId;
        this.owner = owner;
        this.groupName = groupName;
        this.affiliations = affiliations;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getOwner() {
        return owner;
    }

    public Integer getAffiliations() {
        return affiliations;
    }

    @Override public String toString() {
        return "GroupResource{" +
                "groupId='" + groupId + '\'' +
                ", owner='" + owner + '\'' +
                ", groupName='" + groupName + '\'' +
                ", affiliations=" + affiliations +
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
