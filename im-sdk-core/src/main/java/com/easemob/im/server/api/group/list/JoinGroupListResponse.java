package com.easemob.im.server.api.group.list;

import com.easemob.im.server.model.EMPage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class JoinGroupListResponse {

    @JsonProperty("data")
    private List<JoinGroupResource> groups;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public JoinGroupListResponse(@JsonProperty("data") List<JoinGroupResource> groups,
            @JsonProperty("cursor") String cursor) {
        this.groups = groups;
        this.cursor = cursor;
    }

    public List<String> getGroupIds() {
        return groups.stream().map(JoinGroupResource::getGroupId).collect(Collectors.toList());
    }

    public List<JoinGroupResource> getGroups() {
        return groups;
    }

    public EMPage<String> toEMPage() {
        List<String> groupIds =
                groups.stream().map(JoinGroupResource::getGroupId).collect(Collectors.toList());
        return new EMPage<>(groupIds, cursor);
    }

    public EMPage<JoinGroupResource> toEMPageWithInfo() {
        return new EMPage<>(groups, cursor);
    }

    public String getCursor() {
        return cursor;
    }

    @Override
    public String toString() {
        return "JoinGroupListResponse{" +
                "groups=" + groups +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
