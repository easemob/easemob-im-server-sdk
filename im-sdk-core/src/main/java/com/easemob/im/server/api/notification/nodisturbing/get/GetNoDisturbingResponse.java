package com.easemob.im.server.api.notification.nodisturbing.get;

import com.easemob.im.server.model.EMNotificationNoDisturbing;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetNoDisturbingResponse {
    @JsonProperty("entities")
    private List<EMNotificationNoDisturbing> entities;

    @JsonCreator
    public GetNoDisturbingResponse(@JsonProperty("entities") List<EMNotificationNoDisturbing> entities) {
        this.entities = entities;
    }

    public EMNotificationNoDisturbing toNotificationNoDisturbing() {
        return this.entities.get(0);
    }
}
