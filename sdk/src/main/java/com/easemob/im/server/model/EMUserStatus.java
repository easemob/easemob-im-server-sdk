package com.easemob.im.server.model;

public class EMUserStatus {
    private String username;
    private boolean isOnline;

    public EMUserStatus(String username, boolean isOnline) {
        this.username = username;
        this.isOnline = isOnline;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

}
