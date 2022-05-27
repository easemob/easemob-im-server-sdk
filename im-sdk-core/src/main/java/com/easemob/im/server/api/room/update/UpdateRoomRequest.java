package com.easemob.im.server.api.room.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonProperty("need_verify")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean needVerify;

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

    @JsonCreator
    public UpdateRoomRequest(@JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("maxusers") Integer maxMembers,
            @JsonProperty("custom") String custom,
            @JsonProperty("need_verify") Boolean needVerify) {
        this.name = name;
        this.description = description;
        this.maxMembers = maxMembers;
        this.custom = custom;
        this.needVerify = needVerify;
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

    public UpdateRoomRequest withNeedVerify(Boolean needVerify){
        this.needVerify = needVerify;
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
