package com.easemob.im.server.api.message.missed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageMissedCountResponse {
    @JsonProperty("data")
    private Map<String, Integer> missedMessageCountByQueue;

    @JsonCreator
    public MessageMissedCountResponse(@JsonProperty("data") Map<String, Integer> missedMessageCountByQueue) {
        this.missedMessageCountByQueue = missedMessageCountByQueue;
    }

    public List<MissedMessageCount> getMissedMessageCounts() {
        return this.missedMessageCountByQueue.entrySet()
            .stream()
            .map(e -> new MissedMessageCount(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }


}
