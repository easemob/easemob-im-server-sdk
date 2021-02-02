package com.easemob.im.server.model;

import com.easemob.im.server.api.chatgroups.detail.GroupDetailResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class EMGroup {
    private final String groupId;

    public EMGroup(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "EMGroup{" +
            "groupId='" + groupId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EMGroup)) {
            return false;
        }
        EMGroup emGroup = (EMGroup) o;
        return Objects.equals(groupId, emGroup.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

}
