package com.easemob.im.server.api.presence.set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresenceUserStatusSetRequest {

    @JsonProperty("ext")
    private String ext;

    @JsonCreator
    public PresenceUserStatusSetRequest(@JsonProperty("ext") String ext) {
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }
}
