package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.model.EMMetadata;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MetadataGetUserResponse {
    @JsonProperty("data")
    private Map<String, String> data;

    @JsonCreator
    public MetadataGetUserResponse(@JsonProperty("data") Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    public EMMetadata toMetadata() {
        return new EMMetadata(this.data);
    }

    @Override
    public String toString() {
        return "MetadataGetUserResponse{" +
                "data=" + data +
                '}';
    }
}
