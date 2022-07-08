package com.easemob.im.server.api.message.upload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ImportMessageResponse {

    private final String fieldName = "msg_id";
    /**
     * Import message successfully returns data.
     */
    @JsonProperty("data")
    private Map<String, String> messageIdsByReceiverId;

    @JsonCreator
    public ImportMessageResponse(@JsonProperty("data") Map<String, String> messageIdsByReceiverId) {
        this.messageIdsByReceiverId = messageIdsByReceiverId;
    }

    /**
     * Get the message id of the imported message.
     *
     * @return the message id, null if absent
     */
    public String getMessageId() {
        if (this.messageIdsByReceiverId == null || !this.messageIdsByReceiverId.containsKey(
                fieldName)) {
            return null;
        }
        return this.messageIdsByReceiverId.get(fieldName);
    }

}
