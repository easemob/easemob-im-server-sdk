package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.model.EMMetadataBatch;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MetadataBatchGetResponse {
    @JsonProperty("data")
    private Map<String, Map<String, String>> data;

    @JsonCreator
    public MetadataBatchGetResponse(@JsonProperty("data") Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public EMMetadataBatch toMetadataBatch() {
        return new EMMetadataBatch(this.data);
    }

    @Override public String toString() {
        return "MetadataBatchGetResponse{" +
                "data=" + data +
                '}';
    }
}
