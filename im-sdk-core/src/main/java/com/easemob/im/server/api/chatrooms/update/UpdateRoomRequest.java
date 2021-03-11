package com.easemob.im.server.api.chatrooms.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: add other mutable properties
public class UpdateRoomRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("maxusers")
    private Integer maxMembers;

    public UpdateRoomRequest() {
        this.name = null;
        this.description = null;
        this.maxMembers = null;
    }

    @JsonCreator
    public UpdateRoomRequest(@JsonProperty("name") String name,
                             @JsonProperty("description") String description,
                             @JsonProperty("maxusers") Integer maxMembers) {
        this.name = name;
        this.description = description;
        this.maxMembers = maxMembers;
    }

    public UpdateRoomRequest withName(String name) {
        this.name = name;
        return this;
    }

    public UpdateRoomRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public UpdateRoomRequest withMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
        return this;
    }

    public boolean hasName() {
        return this.name != null;
    }

    public boolean hasDescription() {
        return this.description != null;
    }

    public boolean hasMaxMembers() {
        return this.maxMembers != null;
    }
}
