package com.easemob.im.server.model;

public class EMNotificationUserSetting {

    private String username;

    private String nickname;

    private Boolean showMessageContent;

    public EMNotificationUserSetting(String username, String nickname, Boolean showMessageContent) {
        this.username = username;
        this.nickname = nickname;
        this.showMessageContent = showMessageContent;
    }

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public Boolean getShowMessageContent() {
        return this.showMessageContent;
    }

}
