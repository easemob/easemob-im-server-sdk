package com.easemob.im.server.api.block.room.msg.block;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BlockUserSendMsgToRoomRequest {

    @JsonProperty("data")
    private BlockRequest blockRequest;

    public static class BlockRequest {
        @JsonProperty("usernames")
        private List<String> usernames;
        @JsonProperty("mute_duration")
        private long durationInMillis;

        @JsonCreator
        public BlockRequest(@JsonProperty("usernames") List<String> usernames,
                            @JsonProperty("mute_duration") long durationInMillis) {
            this.usernames = usernames;
            this.durationInMillis = durationInMillis;
        }
    }

    @JsonCreator
    public BlockUserSendMsgToRoomRequest(@JsonProperty("data") BlockRequest blockRequest) {
        this.blockRequest = blockRequest;
    }

    public static BlockUserSendMsgToRoomRequest of(String username, Duration duration) {
        List<String> usernames = new ArrayList<>();
        usernames.add(username);

        BlockRequest blockRequest = new BlockRequest(usernames, duration.toMillis());

        return new BlockUserSendMsgToRoomRequest(blockRequest);
    }
}
