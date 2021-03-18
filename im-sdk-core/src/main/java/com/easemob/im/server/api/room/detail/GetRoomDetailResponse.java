package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.model.EMRoom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class GetRoomDetailResponse {
    @JsonProperty("data")
    private List<Room> rooms;

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

        @JsonProperty("affiliations")
        private List<Member> members;

        private static class Member {
            @JsonProperty("member")
            private String member;
            @JsonProperty("owner")
            private String owner;

            @JsonCreator
            public Member(@JsonProperty("member") String member,
                          @JsonProperty("owner") String owner) {
                this.member = member;
                this.owner = owner;
            }

            public String getUsername() {
                return this.member != null ? this.member : this.owner;
            }

        }

        @JsonCreator
        public Room(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("description") String description,
                    @JsonProperty("owner") String owner,
                    @JsonProperty("membersonly") boolean needApprove,
                    @JsonProperty("maxusers") int maxMembers,
                    @JsonProperty("affiliations") List<Member> members) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.owner = owner;
            this.needApprove = needApprove;
            this.maxMembers = maxMembers;
            this.members = members;
        }

        public EMRoom toRoom() {
            return new EMRoom(this.id, this.name, this.description, this.needApprove, this.owner, this.maxMembers,
                    this.members.stream().map(Member::getUsername).collect(Collectors.toList()));
        }
    }

    public GetRoomDetailResponse(@JsonProperty("data") List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<EMRoom> toRoomDetails() {
        return this.rooms.stream().map(Room::toRoom).collect(Collectors.toList());
    }
}
