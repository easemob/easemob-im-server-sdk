package com.easemob.im.server.model;

import java.util.Map;

public class EMSentMessageResults {

    private Map<String, String> messageResultsByEntityId;

    public EMSentMessageResults(Map<String, String> messageResultsByEntityId) {
        this.messageResultsByEntityId = messageResultsByEntityId;
    }

    public Map<String, String> getMessageResultsByEntityId() {
        return this.messageResultsByEntityId;
    }
}
