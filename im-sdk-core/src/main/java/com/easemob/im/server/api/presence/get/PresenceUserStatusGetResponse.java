package com.easemob.im.server.api.presence.get;

import com.easemob.im.server.api.presence.PresenceUserStatusResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresenceUserStatusGetResponse {

    @JsonProperty("result")
    private List<PresenceUserStatusResource> userStatusResources;

    @JsonCreator
    public PresenceUserStatusGetResponse(@JsonProperty("result") List<PresenceUserStatusResource> userStatusResources) {
        this.userStatusResources = userStatusResources;
    }

    public List<PresenceUserStatusResource> getUserStatusResources() {
        return userStatusResources;
    }
}
