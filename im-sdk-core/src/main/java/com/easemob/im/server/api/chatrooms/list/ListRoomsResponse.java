package com.easemob.im.server.api.chatrooms.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class ListRoomsResponse {

    @JsonProperty("data")
    private List<Room> rooms;

    @JsonProperty("cursor")
    private String cursor;

    public static class Room {
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("owner")
        private String owner;
        @JsonProperty("affiliations_count")
        private int members;

        @JsonCreator
        public Room(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("owner") String owner,
                    @JsonProperty("affiliations_count") int members) {
            this.id = id;
            this.name = name;
            this.owner = owner;
            this.members = members;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getOwner() {
            return this.owner;
        }

        public int getMembers() {
            return this.members;
        }

    }

    public List<String> getRoomIds() {
        return this.rooms.stream().map(Room::getId).collect(Collectors.toList());
    }

    public String getCursor() {
        return this.cursor;
    }

    @JsonCreator
    public ListRoomsResponse(@JsonProperty("data") List<Room> rooms) {
        this.rooms = rooms;
    }
}
