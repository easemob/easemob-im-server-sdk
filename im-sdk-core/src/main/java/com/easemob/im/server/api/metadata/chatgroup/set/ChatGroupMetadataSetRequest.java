package com.easemob.im.server.api.metadata.chatgroup.set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ChatGroupMetadataSetRequest {

    @JsonProperty("metaData")
    private Map<String, String> metaData;

    @JsonCreator
    public ChatGroupMetadataSetRequest(@JsonProperty("metaData") Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }
}
