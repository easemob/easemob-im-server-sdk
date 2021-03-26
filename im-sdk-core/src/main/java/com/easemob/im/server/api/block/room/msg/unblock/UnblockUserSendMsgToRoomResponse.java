package com.easemob.im.server.api.block.room.msg.unblock;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UnblockUserSendMsgToRoomResponse {
    @JsonProperty("data")
    private List<Result> results;

    public static class Result {
        @JsonProperty("result")
        private boolean isSuccess;

        @JsonProperty("user")
        private String username;

        @JsonCreator
        public Result(@JsonProperty("result") boolean isSuccess,
                      @JsonProperty("user") String username) {
            this.isSuccess = isSuccess;
            this.username = username;
        }

        public boolean isSuccess() {
            return this.isSuccess;
        }

        public String getUsername() {
            return this.username;
        }
    }

    @JsonCreator
    public UnblockUserSendMsgToRoomResponse(@JsonProperty("data") List<Result> results) {
        this.results = results;
    }

    public boolean isSuccess(String username) {
        return this.results.stream().filter(result -> result.getUsername().equals(username))
                .findFirst().map(result -> result.isSuccess).orElse(false);
    }

}
