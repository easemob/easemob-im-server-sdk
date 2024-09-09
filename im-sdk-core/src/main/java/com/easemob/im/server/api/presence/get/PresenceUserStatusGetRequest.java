package com.easemob.im.server.api.presence.get;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresenceUserStatusGetRequest {

    @JsonProperty("usernames")
    private List<String> usernames;

    @JsonCreator
    public PresenceUserStatusGetRequest(@JsonProperty("usernames") List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }
}
