package com.easemob.im.server.api.message.send;

import com.easemob.im.server.model.EMSentMessageResults;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SendMessageResultResponse {
    /**
     * messageSendResults contains mapping from receiver id to message send reuslt
     */
    @JsonProperty("data")
    private Map<String, String> messageSendResults;

    public SendMessageResultResponse(@JsonProperty("data") Map<String, String> messageSendResults) {
        this.messageSendResults = messageSendResults;
    }

    /**
     * Get message result sent to given receiver.
     *
     * @param receiverId the receiver's id
     * @return the message send result, null if absent
     */
    public String getMessageSendResult(String receiverId) {
        if (this.messageSendResults == null) {
            return null;
        }
        return this.messageSendResults.get(receiverId);
    }

    public EMSentMessageResults toEMSentMessages() {
        return new EMSentMessageResults(this.messageSendResults);
    }

    /**
     * Get all message send results mapped by receiver id.
     *
     * @return message send results mapped by receiver id
     */
    public Map<String, String> getAllMessageSendResultsByReceiver() {
        return this.messageSendResults;
    }
}
