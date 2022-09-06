package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.model.EMRoom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetRoomDetailResponse {
    @JsonProperty("data")
    private List<Room> rooms;

    public GetRoomDetailResponse(@JsonProperty("data") List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<EMRoom> toRoomDetails() {
        return this.rooms.stream().map(Room::toRoom).collect(Collectors.toList());
    }

    private static class Room {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("membersonly")
        private boolean needApprove;

        @JsonProperty("owner")
        private String owner;

        @JsonProperty("maxusers")
        private int maxMembers;

        @JsonProperty("mute")
        private Boolean mute;

        @JsonProperty("affiliations")
        private List<Map<String, String>> members;

        @JsonProperty("affiliations_count")
        private Integer memberCount;

        @JsonProperty("custom")
        private String custom;

        @JsonProperty("created")
        private Long created;

        @JsonCreator
        public Room(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("owner") String owner,
                @JsonProperty("membersonly") boolean needApprove,
                @JsonProperty("maxusers") int maxMembers,
                @JsonProperty("mute") Boolean mute,
                @JsonProperty("affiliations") List<Map<String, String>> members,
                @JsonProperty("affiliations_count") Integer memberCount,
                @JsonProperty("custom") String custom,
                @JsonProperty("created") Long created) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.owner = owner;
            this.needApprove = needApprove;
            this.maxMembers = maxMembers;
            this.mute = mute;
            this.members = members;
            this.memberCount = memberCount;
            this.custom = custom;
            this.created = created;
        }

        public EMRoom toRoom() {
            return new EMRoom(this.id, this.name, this.description, this.needApprove, this.owner,
                    this.maxMembers, this.mute, this.members, this.memberCount, this.custom, this.created);
        }
    }
}
