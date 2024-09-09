package com.easemob.im.server.api.presence.online;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PresenceUserOnlineCountResponse {

    @JsonProperty("result")
    private Integer result;

    @JsonCreator
    public PresenceUserOnlineCountResponse(@JsonProperty("result") Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }
}
