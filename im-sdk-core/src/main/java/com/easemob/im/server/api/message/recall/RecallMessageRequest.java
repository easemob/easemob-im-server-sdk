package com.easemob.im.server.api.message.recall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecallMessageRequest {

    @JsonProperty("msgs")
    private List<RecallMessageSource> recallMessages;

    @JsonCreator
    public RecallMessageRequest(@JsonProperty("msgs") List<RecallMessageSource> recallMessages) {
        this.recallMessages = recallMessages;
    }

}
