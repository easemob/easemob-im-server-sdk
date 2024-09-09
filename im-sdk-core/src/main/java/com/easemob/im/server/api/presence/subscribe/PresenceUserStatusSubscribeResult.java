package com.easemob.im.server.api.presence.subscribe;

import com.easemob.im.server.api.presence.PresenceSubscribeResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresenceUserStatusSubscribeResult {

    @JsonProperty("totalnum")
    private String totalNumber;

    @JsonProperty("sublist")
    private List<PresenceSubscribeResource> subscribeResourceList;

    @JsonCreator
    public PresenceUserStatusSubscribeResult(@JsonProperty("totalnum") String totalNumber,
            @JsonProperty("sublist") List<PresenceSubscribeResource> subscribeResourceList) {
        this.totalNumber = totalNumber;
        this.subscribeResourceList = subscribeResourceList;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public List<PresenceSubscribeResource> getSubscribeResourceList() {
        return subscribeResourceList;
    }
}
