package com.easemob.im.server.api.metadata.chatgroup.get;

import com.easemob.im.server.model.EMMetadataBatch;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ChatGroupMetadataBatchGetResponse {
    @JsonProperty("data")
    private Map<String, Map<String, String>> data;

    @JsonCreator
    public ChatGroupMetadataBatchGetResponse(@JsonProperty("data") Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public EMMetadataBatch toMetadataBatch() {
        return new EMMetadataBatch(this.data);
    }

    @Override public String toString() {
        return "ChatGroupMetadataBatchGetResponse{" +
                "data=" + data +
                '}';
    }
}
