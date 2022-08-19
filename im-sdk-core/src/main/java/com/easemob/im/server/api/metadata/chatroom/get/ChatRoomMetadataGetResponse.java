package com.easemob.im.server.api.metadata.chatroom.get;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ChatRoomMetadataGetResponse {

    private Map<String, String> metaData;

    @JsonCreator
    public ChatRoomMetadataGetResponse(@JsonProperty("data") Map<String, String> result) {
        this.metaData = result;
    }

}
