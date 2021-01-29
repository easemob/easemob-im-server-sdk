package com.easemob.im.server.api.notification.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationUserSetStyleRequest {
    @JsonProperty("notification_display_style")
    private NotificationUser.Style style;

    @JsonCreator
    public NotificationUserSetStyleRequest(@JsonProperty("notification_display_style") NotificationUser.Style style) {
        this.style = style;
    }

    public NotificationUser.Style getStyle() {
        return style;
    }

}
