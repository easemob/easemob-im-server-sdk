package com.easemob.im.server.model;

import java.util.Map;

public class EMSentMessageIds {

    private Map<String, String> messageIdsByEntityId;

    public EMSentMessageIds(Map<String, String> messageIdsByEntityId) {
        this.messageIdsByEntityId = messageIdsByEntityId;
    }

    public Map<String, String> getMessageIdsByEntityId() {
        return this.messageIdsByEntityId;
    }
}