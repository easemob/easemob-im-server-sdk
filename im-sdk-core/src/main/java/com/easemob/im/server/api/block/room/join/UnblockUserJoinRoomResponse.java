package com.easemob.im.server.api.block.room.join;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnblockUserJoinRoomResponse {

    @JsonProperty("data")
    private UnblockUserJoinRoomResource resource;

    @JsonCreator
    public UnblockUserJoinRoomResponse(@JsonProperty("data") UnblockUserJoinRoomResource resource) {
        this.resource = resource;
    }

    public boolean getSuccess() {
        return this.resource != null && this.resource.success;
    }

    public static class UnblockUserJoinRoomResource {
        @JsonProperty("result")
        private boolean success;

        @JsonCreator
        public UnblockUserJoinRoomResource(@JsonProperty("result") boolean success) {
            this.success = success;
        }

    }
}
