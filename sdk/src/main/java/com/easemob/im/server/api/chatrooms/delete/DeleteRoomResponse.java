package com.easemob.im.server.api.chatrooms.delete;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRoomResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("success")
        private boolean success;
        @JsonProperty("id")
        private String id;

        @JsonCreator
        public Wrapper(@JsonProperty("success") boolean success,
                       @JsonProperty("id") String id) {
            this.success = success;
            this.id = id;
        }

        public boolean getSuccess() {
            return this.success;
        }

        public String getId() {
            return this.id;
        }
    }

    public boolean getSuccess() {
        return this.wrapper != null && this.wrapper.getSuccess();
    }

    @JsonCreator
    public DeleteRoomResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }
}
