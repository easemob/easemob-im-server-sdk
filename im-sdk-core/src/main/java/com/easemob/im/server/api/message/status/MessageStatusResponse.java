package com.easemob.im.server.api.message.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MessageStatusResponse {
    @JsonProperty("data")
    private Map<String, String> statusByMessageId;

    @JsonCreator
    public MessageStatusResponse(@JsonProperty("data") Map<String, String> statusByMessageId) {
        this.statusByMessageId = statusByMessageId;
    }

    public boolean isMessageDelivered(String messageId) {
        String delivered = this.statusByMessageId.get(messageId);
        return delivered != null && delivered.equals("delivered");
    }

}
