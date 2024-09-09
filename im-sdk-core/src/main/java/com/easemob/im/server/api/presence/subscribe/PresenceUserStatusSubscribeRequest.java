package com.easemob.im.server.api.presence.subscribe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresenceUserStatusSubscribeRequest {

    @JsonProperty("usernames")
    private List<String> usernames;

    @JsonCreator
    public PresenceUserStatusSubscribeRequest(@JsonProperty("usernames") List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }
}
