package com.easemob.im.server.api.group.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateGroupOwnerRequest {
    @JsonProperty("newowner")
    private String owner;

    @JsonCreator
    public UpdateGroupOwnerRequest(@JsonProperty("newowner") String owner) {
        this.owner = owner;
    }
}
