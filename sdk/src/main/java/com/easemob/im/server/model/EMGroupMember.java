package com.easemob.im.server.model;

public class EMGroupMember {
    public enum Role {
        MEMBER,
        OWNER,
    }
    private String username;
    private Role role;

    private EMGroupMember(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public static EMGroupMember asMember(String username) {
        return new EMGroupMember(username, Role.MEMBER);
    }

    public static EMGroupMember asOwner(String username) {
        return new EMGroupMember(username, Role.OWNER);
    }
}
