package com.easemob.im.server.api.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {

    TXT("txt"),
    TEXT("text"),
    IMG("img"),
    AUDIO("audio"),
    VIDEO("video"),
    LOC("loc"),
    FILE("file"),
    CMD("cmd"),
    CUSTOM("custom");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static MessageType from(String value) {
        for (MessageType t : values()) {
            if (t.value.equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown message type '%s'", value));
    }

}
