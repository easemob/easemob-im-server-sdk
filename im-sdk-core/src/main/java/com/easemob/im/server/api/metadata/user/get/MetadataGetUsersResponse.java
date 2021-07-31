package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.model.EMBatchMetadata;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MetadataGetUsersResponse {
    @JsonProperty("data")
    private Map<String, Map<String, String>> data;

    @JsonCreator
    public MetadataGetUsersResponse(@JsonProperty("data") Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public EMBatchMetadata toBatchMetadata() {
        return new EMBatchMetadata(this.data);
    }

    @Override
    public String toString() {
        return "MetadataGetUserResponse{" +
                "data=" + data +
                '}';
    }
}
