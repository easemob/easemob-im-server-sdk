package com.easemob.im.server.api.room.announcement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomAnnouncementResource {
    @JsonProperty("announcement")
    private String text;

    @JsonCreator
    public RoomAnnouncementResource(@JsonProperty("announcement") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
