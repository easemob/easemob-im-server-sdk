package com.easemob.im.server.api.block.room.join;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetBlockedUsersResponse {

    @JsonProperty("data")
    private List<String> usernames;

    @JsonCreator
    public GetBlockedUsersResponse(@JsonProperty("data") List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return this.usernames;
    }
}
