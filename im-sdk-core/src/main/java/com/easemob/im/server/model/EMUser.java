package com.easemob.im.server.model;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.regex.Pattern;

public class EMUser extends EMEntity {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z][0-9a-z-]{1,32}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^[a-zA-Z0-9~!@#$%^&*\\-_=+<>;:,./?]{1,32}$");

    private final String username;

    private final String uuid;

    private final Boolean canLogin;

    public EMUser(String username, String uuid, Boolean canLogin) {
        super(EntityType.USER);
        super.id(username);
        this.username = username;
        this.uuid = uuid;
        this.canLogin = canLogin;
    }

    public static void validateUsername(String username) {
        if (Strings.isBlank(username)) {
            throw new EMInvalidArgumentException("username must not be null or blank");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new EMInvalidArgumentException(
                    String.format("username '%s' should match regex %s", username,
                            USERNAME_PATTERN.toString()));
        }
    }

    public static void validatePassword(String password) {
        if (Strings.isBlank(password)) {
            throw new EMInvalidArgumentException("password must not be null or empty");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // DO NOT show password
            throw new EMInvalidArgumentException(
                    String.format("password should match regex %s", PASSWORD_PATTERN.toString()));
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getUuid() {
        return this.uuid;
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
                ", uuid='" + uuid + '\'' +
                ", canLogin=" + canLogin +
                '}';
    }
}
