package com.easemob.im.server.model;

import com.easemob.im.server.exception.EMInvalidArgumentException;

import java.util.Objects;

public class EMUser {
    private final String username;

    public EMUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new EMInvalidArgumentException("username must not be null or empty");
        }
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EMUser)) {
            return false;
        }
        EMUser emUser = (EMUser) o;
        return username.equals(emUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
