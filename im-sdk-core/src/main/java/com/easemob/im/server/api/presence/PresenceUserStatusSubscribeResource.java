package com.easemob.im.server.api.presence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PresenceUserStatusSubscribeResource {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("last_time")
    private String lastTime;

    @JsonProperty("ext")
    private String ext;

    @JsonProperty("expiry")
    private String expiry;

    @JsonProperty("status")
    private Object status;

    @JsonCreator
    public PresenceUserStatusSubscribeResource(@JsonProperty("uid") String uid,
                              @JsonProperty("last_time") String lastTime,
                              @JsonProperty("ext") String ext,
                              @JsonProperty("expiry") String expiry,
                              @JsonProperty("status") Object status) {
        this.uid = uid;
        this.lastTime = lastTime;
        this.ext = ext;
        this.expiry = expiry;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getExt() {
        return ext;
    }

    public String getExpiry() {
        return expiry;
    }

    public Object getStatus() {
        return status;
    }
}
