package com.easemob.im.server.api.chatrooms.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRoomResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    public CreateRoomResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public static class Wrapper {
        @JsonProperty("id")
        private String roomId;

        public Wrapper(@JsonProperty("id") String roomId) {
            this.roomId = roomId;
        }

        public String getRoomId() {
            return this.roomId;
        }
    }

    public String getRoomId() {
        return this.wrapper != null ? this.wrapper.roomId : null;
    }
}
