package com.easemob.im.server.api.metadata.user.set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MetadataSetResponse {
    @JsonProperty("data")
    private Map<String, String> data;

    @JsonCreator
    public MetadataSetResponse(@JsonProperty("data") Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MetadataSetResponse{" +
                "data=" + data +
                '}';
    }
}
