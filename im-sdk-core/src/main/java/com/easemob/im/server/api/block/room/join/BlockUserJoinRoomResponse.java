package com.easemob.im.server.api.block.room.join;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockUserJoinRoomResponse {

    @JsonProperty("data")
    private BlockUserJoinRoomResource resource;

    @JsonCreator
    public BlockUserJoinRoomResponse(@JsonProperty("data") BlockUserJoinRoomResource resource) {
        this.resource = resource;
    }

    public boolean isSuccess() {
        return this.resource != null && this.resource.success;
    }

    public static class BlockUserJoinRoomResource {
        @JsonProperty("result")
        private boolean success;

        @JsonCreator
        public BlockUserJoinRoomResource(@JsonProperty("result") boolean success) {
            this.success = success;
        }

    }
}
