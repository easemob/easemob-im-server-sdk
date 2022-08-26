package com.easemob.im.server.api.metadata.chatroom.get;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ChatRoomMetadataGetResponse {

    private Map<String, String> metadata;

    @JsonCreator
    public ChatRoomMetadataGetResponse(@JsonProperty("data") Map<String, String> result) {
        this.metadata = result;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

}
