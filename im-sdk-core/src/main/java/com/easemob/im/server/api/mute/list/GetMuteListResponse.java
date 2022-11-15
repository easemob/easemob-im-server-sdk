package com.easemob.im.server.api.mute.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMuteListResponse {

    @JsonProperty("data")
    private Object muteData;

    @JsonCreator
    public GetMuteListResponse(@JsonProperty("data") Object muteData) {
        this.muteData = muteData;
    }

    public Object getMuteData() {
        return muteData;
    }
}
