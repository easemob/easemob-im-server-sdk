package com.easemob.im.server.api.notification.settings;

import com.easemob.im.server.model.EMNotificationUserSetting;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationUserSettingGetResponse {

    @JsonProperty("entities")
    private List<NotificationUserSettingResource> settings;

    @JsonCreator
    public NotificationUserSettingGetResponse(@JsonProperty("entities") List<NotificationUserSettingResource> settings) {
        this.settings = settings;
    }

    public List<EMNotificationUserSetting> getEMNotificationSetting() {
        return this.settings.stream().map(NotificationUserSettingResource::toEMNotificationSettings).collect(Collectors.toList());
    }

    public EMNotificationUserSetting getEMNotificationSetting(String username) {
        return this.settings.stream().map(NotificationUserSettingResource::toEMNotificationSettings)
                .filter(settings -> settings.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }
}
