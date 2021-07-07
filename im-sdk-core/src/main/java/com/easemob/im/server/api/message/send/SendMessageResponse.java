package com.easemob.im.server.api.message.send;

import com.easemob.im.server.model.EMSentMessageIds;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SendMessageResponse {
    /**
     * messageIdsByReceiverId contains mapping from receiver id to message id
     */
    @JsonProperty("data")
    private Map<String, String> messageIdsByReceiverId;

    public SendMessageResponse(@JsonProperty("data") Map<String, String> messageIdsByReceiverId) {
        this.messageIdsByReceiverId = messageIdsByReceiverId;
    }

    /**
     * Get message id sent to given receiver.
     *
     * @param receiverId the receiver's id
     * @return the message id, null if absent
     */
    public String getMessageIdByReceiverId(String receiverId) {
        if (this.messageIdsByReceiverId == null) {
            return null;
        }
        return this.messageIdsByReceiverId.get(receiverId);
    }

    public EMSentMessageIds toEMSentMessages() {
        return new EMSentMessageIds(this.messageIdsByReceiverId);
    }

    /**
     * Get all message ids mapped by receiver id.
     *
     * @return message ids mapped by receiver id
     */
    public Map<String, String> getAllMessageIdsByReceiver() {
        return this.messageIdsByReceiverId;
    }
}
