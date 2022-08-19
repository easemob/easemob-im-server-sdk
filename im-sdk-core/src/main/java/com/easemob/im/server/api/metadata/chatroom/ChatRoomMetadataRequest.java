package com.easemob.im.server.api.metadata.chatroom;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class ChatRoomMetadataRequest {

    @JsonProperty("metaData")
    private Map<String, String> metaData;

    @JsonProperty("autoDelete")
    private AutoDelete autoDelete = AutoDelete.DELETE;

    @JsonProperty("notificationExt")
    private String notificationExt;

    @JsonProperty("keys")
    private List<String> keys;

    public ChatRoomMetadataRequest(Map<String, String> metaData,
            AutoDelete autoDelete) {
        this.autoDelete = autoDelete;
        this.metaData = metaData;
    }

    public ChatRoomMetadataRequest(List<String> keys) {
        this.keys = keys;
    }

}
