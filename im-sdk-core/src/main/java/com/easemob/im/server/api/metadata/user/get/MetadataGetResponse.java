package com.easemob.im.server.api.metadata.user.get;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MetadataGetResponse {
    @JsonProperty("data")
    private Map<String, String> data;

    @JsonCreator
    public MetadataGetResponse(@JsonProperty("data") Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MetadataGetResponse{" +
                "data=" + data +
                '}';
    }
}
