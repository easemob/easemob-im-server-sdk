package com.easemob.im.server.api.group.announcement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupAnnouncementGetResponse {
    @JsonProperty("data")
    private GroupAnnouncementResource resource;

    @JsonCreator
    public GroupAnnouncementGetResponse(@JsonProperty("data") GroupAnnouncementResource resource) {
        this.resource = resource;
    }

    public String getAnnouncement() {
        return resource.getText();
    }

}
