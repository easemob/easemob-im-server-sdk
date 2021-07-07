package com.easemob.im.server.api.block.room.msg.block;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlockUserSendMsgToRoomResponse {

    @JsonProperty("data")
    private List<BlockResult> results;

    @JsonCreator
    public BlockUserSendMsgToRoomResponse(@JsonProperty("data") List<BlockResult> results) {
        this.results = results;
    }

    public boolean getSuccess(String username) {
        return this.results.stream()
                .filter(result -> result.getUsername().equals(username))
                .findFirst()
                .map(result -> result.isSuccess)
                .orElse(false);
    }

    public static class BlockResult {
        @JsonProperty("result")
        private boolean isSuccess;

        @JsonProperty("expire")
        private long expireTimestamp;

        @JsonProperty("user")
        private String username;

        @JsonCreator
        public BlockResult(@JsonProperty("result") boolean isSuccess,
                @JsonProperty("expire") long expireTimestamp,
                @JsonProperty("user") String username) {
            this.isSuccess = isSuccess;
            this.expireTimestamp = expireTimestamp;
            this.username = username;
        }

        public String getUsername() {
            return this.username;
        }

        public boolean getSuccess() {
            return this.isSuccess;
        }
    }
}
