package com.easemob.im.server.api.block.group.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UnblockUserSendMsgToGroupResponse {
    @JsonProperty("data")
    private List<UnblockUsersResource> resources;

    @JsonCreator
    public UnblockUserSendMsgToGroupResponse(@JsonProperty("data") List<UnblockUsersResource> resources) {
        this.resources = resources;
    }

    public boolean getSuccess(String username) {
        return this.resources.stream()
                .filter(res -> res.getUsername().equals(username))
                .findFirst().map(UnblockUsersResource::getSuccess)
                .orElse(false);
    }

    public static class UnblockUsersResource {
        @JsonProperty("result")
        private boolean success;

        @JsonProperty("user")
        private String username;

        @JsonCreator
        public UnblockUsersResource(@JsonProperty("result") boolean success,
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