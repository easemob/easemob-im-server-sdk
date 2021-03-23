package com.easemob.im.server.api.block.room.msg.block;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BlockUserSendMsgToRoomRequest {

    @JsonProperty("usernames")
    private List<String> usernames;

    @JsonProperty("mute_duration")
    private long durationInMillis;

    @JsonCreator
    public BlockUserSendMsgToRoomRequest(@JsonProperty("usernames") List<String> usernames,
                                         @JsonProperty("mute_duration") long durationInMillis) {
        this.usernames = usernames;
        this.durationInMillis = durationInMillis;
    }

    public static BlockUserSendMsgToRoomRequest of(String username) {
        List<String> usernames = new ArrayList<>();
        usernames.add(username);
        return new BlockUserSendMsgToRoomRequest(usernames, -1);
    }

    public static BlockUserSendMsgToRoomRequest of(String username, Duration duration) {
        List<String> usernames = new ArrayList<>();
        usernames.add(username);
        return new BlockUserSendMsgToRoomRequest(usernames, duration.toMillis());
    }
}
