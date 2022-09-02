package com.easemob.im.server.api.metadata.chatroom.delete;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class ChatRoomMetadataDeleteResponse {

    private List<String> successKeys;

    private Map<String, String> errorKeys;

    @JsonCreator
    public ChatRoomMetadataDeleteResponse(@JsonProperty("data") Map<String, Object> result) {
        if (result != null) {
            if (result.containsKey("successKeys")) {
                successKeys = (List<String>) result.get("successKeys");
            }
            if (result.containsKey("errorKeys")) {
                errorKeys = (Map<String, String>) result.get("errorKeys");
            }
        }
    }

}
