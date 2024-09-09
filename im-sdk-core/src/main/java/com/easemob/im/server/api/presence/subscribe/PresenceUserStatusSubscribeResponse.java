package com.easemob.im.server.api.presence.subscribe;

import com.easemob.im.server.api.presence.PresenceUserStatusSubscribeResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresenceUserStatusSubscribeResponse {

    @JsonProperty("result")
    private List<PresenceUserStatusSubscribeResource> userStatusSubscribeResources;

    @JsonCreator
    public PresenceUserStatusSubscribeResponse(@JsonProperty("result") List<PresenceUserStatusSubscribeResource> userStatusSubscribeResources) {
        this.userStatusSubscribeResources = userStatusSubscribeResources;
    }

    public List<PresenceUserStatusSubscribeResource> getUserStatusSubscribeResources() {
        return userStatusSubscribeResources;
    }
}
