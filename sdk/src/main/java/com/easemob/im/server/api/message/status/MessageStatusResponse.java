package com.easemob.im.server.api.message.status;

import com.easemob.im.server.model.EMMessageStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageStatusResponse {
    @JsonProperty("data")
    private Map<String, String> statusByMessageId;

    @JsonCreator
    public MessageStatusResponse(@JsonProperty("data") Map<String, String> statusByMessageId) {
        this.statusByMessageId = statusByMessageId;
    }

    public List<EMMessageStatus> getMessageStatuses() {
        return this.statusByMessageId.entrySet()
            .stream()
            .map(e -> {
                boolean isDelivered = e.getValue().equals("delivered");
                return new EMMessageStatus(e.getKey(), isDelivered);
            }).collect(Collectors.toList());
    }
}
