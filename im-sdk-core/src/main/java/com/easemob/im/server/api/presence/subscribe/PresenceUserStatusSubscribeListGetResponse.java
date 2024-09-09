package com.easemob.im.server.api.presence.subscribe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PresenceUserStatusSubscribeListGetResponse {

    @JsonProperty("result")
    private PresenceUserStatusSubscribeResult result;

    @JsonCreator
    public PresenceUserStatusSubscribeListGetResponse(@JsonProperty("result") PresenceUserStatusSubscribeResult result) {
        this.result = result;
    }

    public PresenceUserStatusSubscribeResult getResult() {
        return result;
    }
}
