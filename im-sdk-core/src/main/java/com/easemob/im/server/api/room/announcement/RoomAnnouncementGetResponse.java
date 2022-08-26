package com.easemob.im.server.api.room.announcement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomAnnouncementGetResponse {
    @JsonProperty("data")
    private RoomAnnouncementResource resource;

    @JsonCreator
    public RoomAnnouncementGetResponse(@JsonProperty("data") RoomAnnouncementResource resource) {
        this.resource = resource;
    }

    public String getAnnouncement() {
        return resource.getText();
    }

}
