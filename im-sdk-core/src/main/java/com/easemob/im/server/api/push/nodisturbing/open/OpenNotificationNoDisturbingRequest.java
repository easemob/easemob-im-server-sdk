package com.easemob.im.server.api.push.nodisturbing.open;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenNotificationNoDisturbingRequest {

    @JsonProperty("notification_no_disturbing")
    private Boolean noDisturbing;

    @JsonProperty("notification_no_disturbing_start")
    private String startTime;

    @JsonProperty("notification_no_disturbing_end")
    private String endTime;

    @JsonCreator
    public OpenNotificationNoDisturbingRequest(
            @JsonProperty("notification_no_disturbing_start") String startTime,
            @JsonProperty("notification_no_disturbing_end") String endTime) {
        this.noDisturbing = true;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
