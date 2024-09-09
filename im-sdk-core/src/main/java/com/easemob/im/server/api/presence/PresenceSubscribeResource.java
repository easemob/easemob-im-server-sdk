package com.easemob.im.server.api.presence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PresenceSubscribeResource {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("expiry")
    private String expiry;

    @JsonCreator
    public PresenceSubscribeResource(@JsonProperty("uid") String uid,
                              @JsonProperty("expiry") String expiry) {
        this.uid = uid;
        this.expiry = expiry;
    }

    public String getUid() {
        return uid;
    }

    public String getExpiry() {
        return expiry;
    }
}
