package com.easemob.im.server.api.block.group.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlockUserSendMsgToGroupResponse {
    @JsonProperty("data")
    private List<BlockUsersResource> resources;

    @JsonCreator
    public BlockUserSendMsgToGroupResponse(@JsonProperty("data") List<BlockUsersResource> resources) {
        this.resources = resources;
    }

    public boolean getSuccess(String username) {
        return this.resources.stream()
                .filter(res -> res.getUsername().equals(username))
                .findFirst().map(BlockUsersResource::getSuccess)
                .orElse(false);
    }

    public static class BlockUsersResource {
        @JsonProperty("result")
        private boolean success;

        @JsonProperty("user")
        private String username;

        @JsonCreator
        public BlockUsersResource(@JsonProperty("result") boolean success,
                                          @JsonProperty("user") String username) {
            this.success = success;
            this.username = username;
        }

        public boolean getSuccess() {
            return this.success;
        }

        public String getUsername() {
            return this.username;
        }
    }
}