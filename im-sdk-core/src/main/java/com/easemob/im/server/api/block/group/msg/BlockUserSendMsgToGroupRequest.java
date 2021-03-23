package com.easemob.im.server.api.block.group.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BlockUserSendMsgToGroupRequest {
    @JsonProperty("usernames")
    private List<String> usernames;

    @JsonProperty("mute_duration")
    private long durationInMillis;

    @JsonCreator
    public BlockUserSendMsgToGroupRequest(@JsonProperty("usernames") List<String> usernames,
                                          @JsonProperty("mute_duration") long durationInMillis) {
        this.usernames = usernames;
        this.durationInMillis = durationInMillis;
    }

    public static BlockUserSendMsgToGroupRequest of(String username, Duration duration) {
        List<String> usernames = new ArrayList<>();
        usernames.add(username);
        return new BlockUserSendMsgToGroupRequest(usernames, duration == null ? -1 : duration.toMillis());
    }
}
