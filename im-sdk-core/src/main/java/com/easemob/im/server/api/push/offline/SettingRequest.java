package com.easemob.im.server.api.push.offline;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingRequest {

    @JsonProperty("type")
    private String type;

    @JsonProperty("ignoreInterval")
    private String ignoreInterval;

    @JsonProperty("ignoreDuration")
    private long ignoreDuration;

    @JsonCreator
    public SettingRequest(@JsonProperty("type") String type,
            @JsonProperty("ignoreInterval") String ignoreInterval,
            @JsonProperty("ignoreDuration") long ignoreDuration) {
        this.type = type;
        this.ignoreInterval = ignoreInterval;
        this.ignoreDuration = ignoreDuration;
    }

    public String getType() {
        return type;
    }

    public String getIgnoreInterval() {
        return ignoreInterval;
    }

    public long getIgnoreDuration() {
        return ignoreDuration;
    }
}
