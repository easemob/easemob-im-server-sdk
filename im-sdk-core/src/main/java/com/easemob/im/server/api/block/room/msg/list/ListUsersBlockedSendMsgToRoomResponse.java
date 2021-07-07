package com.easemob.im.server.api.block.room.msg.list;

import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ListUsersBlockedSendMsgToRoomResponse {
    @JsonProperty("data")
    private List<BlockedUser> blockedUsers;

    @JsonCreator
    public ListUsersBlockedSendMsgToRoomResponse(
            @JsonProperty("data") List<BlockedUser> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public List<EMBlock> getEMBlocks() {
        return this.blockedUsers.stream().map(BlockedUser::toEMBlock).collect(Collectors.toList());
    }

    public static class BlockedUser {
        @JsonProperty("expire")
        private long expireAt;
        @JsonProperty("user")
        private String username;

        public BlockedUser(@JsonProperty("expire") long expireAt,
                @JsonProperty("user") String username) {
            this.expireAt = expireAt;
            this.username = username;
        }

        public EMBlock toEMBlock() {
            return new EMBlock(this.username, Instant.ofEpochMilli(this.expireAt));
        }
    }
}
