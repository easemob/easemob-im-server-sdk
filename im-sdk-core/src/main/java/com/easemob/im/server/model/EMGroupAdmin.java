package com.easemob.im.server.model;

public class EMGroupAdmin {

    private String username;

    private String groupId;

    public EMGroupAdmin(String username, String groupId) {
        this.username = username;
        this.groupId = groupId;
    }

    public String getUsername() {
        return username;
    }

    public String getGroupId() {
        return groupId;
    }

}
