package com.easemob.im.server.model;

import com.easemob.im.server.exception.EMInvalidArgumentException;

import java.util.Objects;
import java.util.regex.Pattern;

public class EMUser {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-z][0-9a-z-]{7,31}");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[0-9a-zA-Z-_?!.]{8,32}");

    private final String username;

    private final Boolean canLogin;

    public EMUser(String username) {
        this(username, null);
    }

    public EMUser(String username, Boolean canLogin) {
        if (username == null || username.isEmpty()) {
            throw new EMInvalidArgumentException("username must not be null or empty");
        }
        this.username = username;
        this.canLogin = canLogin;
    }

    public static void validateUsername(String username) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new EMInvalidArgumentException(String.format("username should match regex %s", USERNAME_PATTERN.toString()));
        }
    }

    public static void validatePassword(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new EMInvalidArgumentException(String.format("password should match regex %s", PASSWORD_PATTERN.toString()));
        }
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getCanLogin() {
        return this.canLogin;
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

    @Override
    public String toString() {
        return "EMUser{" +
                "username='" + username + '\'' +
                ", restricted=" + canLogin +
                '}';
    }
}
