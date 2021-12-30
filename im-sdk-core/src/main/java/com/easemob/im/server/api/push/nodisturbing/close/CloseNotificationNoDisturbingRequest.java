package com.easemob.im.server.api.push.nodisturbing.close;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloseNotificationNoDisturbingRequest {
    @JsonProperty("notification_no_disturbing")
    private Boolean noDisturbing;

    @JsonCreator
    public CloseNotificationNoDisturbingRequest() {
        this.noDisturbing = false;
    }
}
