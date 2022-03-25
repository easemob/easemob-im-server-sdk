package com.easemob.im.server.api.room.update;

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

    @JsonProperty("custom")
    private String custom;

    public UpdateRoomRequest() {
        this.name = null;
        this.description = null;
        this.maxMembers = null;
        this.custom = null;
    }

    @JsonCreator
    public UpdateRoomRequest(@JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("maxusers") Integer maxMembers) {
        this.name = name;
        this.description = description;
        this.maxMembers = maxMembers;
    }

    @JsonCreator
    public UpdateRoomRequest(@JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("maxusers") Integer maxMembers,
            @JsonProperty("custom") String custom) {
        this.name = name;
        this.description = description;
        this.maxMembers = maxMembers;
        this.custom = custom;
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

    public UpdateRoomRequest withCustom(String custom) {
        this.custom = custom;
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

    public boolean hasCustom() {
        return this.custom != null;
    }
}
