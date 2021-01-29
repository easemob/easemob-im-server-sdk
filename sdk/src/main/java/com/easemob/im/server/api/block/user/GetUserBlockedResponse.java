package com.easemob.im.server.api.block.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetUserBlockedResponse {
    @JsonProperty("data")
    private List<String> usernames;

    @JsonCreator
    public GetUserBlockedResponse(@JsonProperty("data") List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }

}
