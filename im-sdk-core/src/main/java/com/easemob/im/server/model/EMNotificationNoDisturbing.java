package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EMNotificationNoDisturbing {
    @JsonProperty("notification_no_disturbing")
    private Boolean noDisturbing;

    @JsonProperty("notification_no_disturbing_start")
    private Integer noDisturbingStart;

    @JsonProperty("notification_no_disturbing_end")
    private Integer noDisturbingEnd;

    @JsonCreator
    public EMNotificationNoDisturbing(@JsonProperty("notification_no_disturbing") Boolean noDisturbing,
                              @JsonProperty("notification_no_disturbing_start") Integer noDisturbingStart,
                              @JsonProperty("notification_no_disturbing_end") Integer noDisturbingEnd) {
        this.noDisturbing = noDisturbing;
        this.noDisturbingStart = noDisturbingStart;
        this.noDisturbingEnd = noDisturbingEnd;
    }

    public Boolean getNoDisturbing() {
        return noDisturbing;
    }

    public Integer getNoDisturbingStart() {
        return noDisturbingStart;
    }

    public Integer getNoDisturbingEnd() {
        return noDisturbingEnd;
    }

    public void setNoDisturbing(Boolean noDisturbing) {
        this.noDisturbing = noDisturbing;
    }

    public void setNoDisturbingStart(Integer noDisturbingStart) {
        this.noDisturbingStart = noDisturbingStart;
    }

    public void setNoDisturbingEnd(Integer noDisturbingEnd) {
        this.noDisturbingEnd = noDisturbingEnd;
    }

    @Override
    public String toString() {
        return "EMNotificationNoDisturbing{" +
                "noDisturbing=" + noDisturbing +
                ", noDisturbingStart=" + noDisturbingStart +
                ", noDisturbingEnd=" + noDisturbingEnd +
                '}';
    }
}
