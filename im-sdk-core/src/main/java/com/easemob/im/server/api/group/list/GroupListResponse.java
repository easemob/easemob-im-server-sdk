package com.easemob.im.server.api.group.list;

import com.easemob.im.server.model.EMPage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
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

    public List<String> getGroupIds() {
        return groups.stream().map(GroupResource::getGroupId).collect(Collectors.toList());
    }

    public List<GroupResource> getGroups() {
        return groups;
    }

    public EMPage<String> toEMPage() {
        List<String> groupIds =
                groups.stream().map(GroupResource::getGroupId).collect(Collectors.toList());
        return new EMPage<>(groupIds, cursor);
    }

    public EMPage<GroupResource> toEMPageWithInfo() {
        return new EMPage<>(groups, cursor);
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
}
