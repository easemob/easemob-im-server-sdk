package com.easemob.im.server.api.metadata.chatroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AutoDelete {

    NO_DELETE,
    DELETE;

    @JsonCreator
    public static AutoDelete parse(String deleteType) {
        if (deleteType == null || deleteType.equals("")) {
            return NO_DELETE;
        }
        for (AutoDelete type : values()) {
            if (type.toString().equalsIgnoreCase(deleteType)) {
                return type;
            }
        }
        return NO_DELETE;
    }

    @JsonValue
    public String toString() {
        return name().toUpperCase();
    }

}
