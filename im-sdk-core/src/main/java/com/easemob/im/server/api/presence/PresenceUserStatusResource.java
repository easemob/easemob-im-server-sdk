package com.easemob.im.server.api.presence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PresenceUserStatusResource {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("last_time")
    private String lastTime;

    @JsonProperty("ext")
    private String ext;

    @JsonProperty("status")
    private Object status;

    @JsonCreator
    public PresenceUserStatusResource(@JsonProperty("uid") String uid,
                              @JsonProperty("last_time") String lastTime,
                              @JsonProperty("ext") String ext,
                              @JsonProperty("status") Object status) {
        this.uid = uid;
        this.lastTime = lastTime;
        this.ext = ext;
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

    public Object getStatus() {
        return status;
    }
}
