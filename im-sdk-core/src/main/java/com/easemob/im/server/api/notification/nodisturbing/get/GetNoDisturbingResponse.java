package com.easemob.im.server.api.notification.nodisturbing.get;

import com.easemob.im.server.model.EMNotificationUserSetting;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetNoDisturbingResponse {
    @JsonProperty("entities")
    private List<EMNotificationUserSetting> entities;

    @JsonCreator
    public GetNoDisturbingResponse(@JsonProperty("entities") List<EMNotificationUserSetting> entities) {
        this.entities = entities;
    }

    public EMNotificationUserSetting toNotificationNoDisturbing() {
        return this.entities.get(0);
    }
}
