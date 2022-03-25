package com.easemob.im.server.api.room.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateRoomRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("maxusers")
    private int maxMembers;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("members")
    private List<String> members;
    @JsonProperty("scale")
    private String scale;
    @JsonProperty("custom")
    private String custom;

    private CreateRoomRequest(String name, String description, String owner, List<String> members,
            int maxMembers) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.scale = "large";
    }

    private CreateRoomRequest(String name, String description, String owner, List<String> members,
            int maxMembers, String custom) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.scale = "large";
        this.custom = custom;
    }

    public static CreateRoomRequest of(String name, String description, String owner,
            List<String> members, int maxMembers) {
        return new CreateRoomRequest(name, description, owner, members, maxMembers);
    }

    public static CreateRoomRequest of(String name, String description, String owner,
            List<String> members, int maxMembers, String custom) {
        return new CreateRoomRequest(name, description, owner, members, maxMembers, custom);
    }
}
