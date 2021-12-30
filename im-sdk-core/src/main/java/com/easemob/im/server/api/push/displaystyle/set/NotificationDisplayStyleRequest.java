package com.easemob.im.server.api.push.displaystyle.set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationDisplayStyleRequest {

    @JsonProperty("notification_display_style")
    private String displayStyle;

    @JsonCreator
    public NotificationDisplayStyleRequest(@JsonProperty("notification_display_style") String displayStyle) {
        this.displayStyle = displayStyle;
    }
}
