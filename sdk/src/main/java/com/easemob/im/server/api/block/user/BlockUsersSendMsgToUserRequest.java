package com.easemob.im.server.api.block.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlockUsersSendMsgToUserRequest {
    @JsonProperty("usernames")
    private List<String> usernames;

    @JsonCreator
    public BlockUsersSendMsgToUserRequest(@JsonProperty("usernames") List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }

}
