package com.easemob.im.server.api.block.group.msg;

import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class GetUsersBlockedSendMsgToGroupResponse {
    @JsonProperty("data")
    private List<UserBlockedSendMsgToGroupResource> resources;

    @JsonCreator
    public GetUsersBlockedSendMsgToGroupResponse(
            @JsonProperty("data") List<UserBlockedSendMsgToGroupResource> resources) {
        this.resources = resources;
    }

    public List<EMBlock> getEMBlocks() {
        return this.resources.stream().map(UserBlockedSendMsgToGroupResource::toEMBlock)
                .collect(Collectors.toList());
    }

    public static class UserBlockedSendMsgToGroupResource {
        @JsonProperty("user")
        private String username;
        @JsonProperty("expire")
        private long expireTimestamp;

        public UserBlockedSendMsgToGroupResource(@JsonProperty("user") String username,
                @JsonProperty("expire") long expireTimestamp) {
            this.username = username;
            this.expireTimestamp = expireTimestamp;
        }

        public EMBlock toEMBlock() {
            return new EMBlock(this.username, Instant.ofEpochMilli(this.expireTimestamp));
        }
    }
}
