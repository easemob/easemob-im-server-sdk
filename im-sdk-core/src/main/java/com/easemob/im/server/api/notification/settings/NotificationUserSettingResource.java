package com.easemob.im.server.api.notification.settings;

import com.easemob.im.server.model.EMNotificationUserSetting;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.concurrent.Executors;

public class NotificationUserSettingResource {
    @JsonProperty("username")
    private String username;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("notification_display_style")
    private Integer showMessageContent;

    @JsonCreator
    public NotificationUserSettingResource(@JsonProperty("username") String username,
                                           @JsonProperty("nickname") String nickname,
                                           @JsonProperty("notification_display_style") Integer showMessageContent) {
        this.username = username;
        this.nickname = nickname;
        this.showMessageContent = showMessageContent;
    }

    public EMNotificationUserSetting toEMNotificationSettings() {
        return new EMNotificationUserSetting(this.username, this.nickname, this.showMessageContent == null ? null : this.showMessageContent == 1);
    }

}