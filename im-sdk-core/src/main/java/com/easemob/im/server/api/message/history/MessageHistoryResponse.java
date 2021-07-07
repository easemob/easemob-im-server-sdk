package com.easemob.im.server.api.message.history;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MessageHistoryResponse {
    @JsonProperty("data")
    private List<MessageHistory> messageHistories;

    @JsonCreator
    public MessageHistoryResponse(@JsonProperty("data") List<MessageHistory> messageHistories) {
        this.messageHistories = messageHistories;
    }

    public String getUrl() {
        return this.messageHistories == null || this.messageHistories.isEmpty() ?
                null :
                this.messageHistories.get(0).getUrl();
    }

    public static class MessageHistory {
        @JsonProperty("url")
        private String url;

        @JsonCreator
        public MessageHistory(@JsonProperty("url") String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }
    }

}
