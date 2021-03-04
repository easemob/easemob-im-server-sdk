package com.easemob.im.server.api.group.announcement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupAnnouncementResource {
    @JsonProperty("announcement")
    private String text;

    @JsonCreator
    public GroupAnnouncementResource(@JsonProperty("announcement") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
