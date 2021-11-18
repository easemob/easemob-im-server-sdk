package com.easemob.im.server.model;

public class EMUserStatus {

    private final String username;

    private final Boolean online;

    public EMUserStatus(String username, Boolean online) {
        this.username = username;
        this.online = online;
    }

    public String getUsername() {
        return this.username;
    }

    public Boolean isOnline() {
        return this.online;
    }
}
