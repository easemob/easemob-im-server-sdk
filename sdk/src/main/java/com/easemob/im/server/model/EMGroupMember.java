package com.easemob.im.server.model;

import java.util.Objects;

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

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "EMGroupMember{" +
            "username='" + username + '\'' +
            ", role=" + role +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EMGroupMember)) {
            return false;
        }
        EMGroupMember that = (EMGroupMember) o;
        return Objects.equals(username, that.username) &&
            role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }

}
