package com.easemob.im.server.api.chatgroups.crud;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupUpdateOwnerRequest {
    @JsonProperty("newowner")
    private String owner;

    @JsonCreator
    public GroupUpdateOwnerRequest(@JsonProperty("newowner") String owner) {
        this.owner = owner;
    }
}
