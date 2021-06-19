package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EMNotificationUserSetting {

    @JsonProperty("notification_no_disturbing")
    private Boolean noDisturbing;

    @JsonProperty("notification_no_disturbing_start")
    private Integer noDisturbingStart;

    @JsonProperty("notification_no_disturbing_end")
    private Integer noDisturbingEnd;

    @JsonCreator
    public EMNotificationUserSetting(
            @JsonProperty("notification_no_disturbing") Boolean noDisturbing,
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

    // TODO: Ken remove these ???
    private String username;
    private String nickname;
    private Boolean showMessageContent;
    public String getUsername() {
        return this.username;
    }
    public String getNickname() {
        return this.nickname;
    }
    public Boolean getShowMessageContent() {
        return this.showMessageContent;
    }
    public EMNotificationUserSetting(String username, String nickname, Boolean showMessageContent) {
        this.username = username;
        this.nickname = nickname;
        this.showMessageContent = showMessageContent;
    }
}
