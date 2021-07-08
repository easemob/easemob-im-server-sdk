package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.util.Strings;

public class UserTokenRequest implements TokenRequest {
    @SuppressWarnings("java:S1170")
    @JsonProperty("grant_type")
    private final String grantType = "password";

    @JsonProperty("username")
    private final String username;

    @JsonProperty("password")
    private final String password;

    private UserTokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonCreator
    public static UserTokenRequest of(@JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        if (Strings.isBlank(username)) {
            throw new EMInvalidArgumentException("username must not be null or blank");
        }
        if (Strings.isBlank(password)) {
            throw new EMInvalidArgumentException("password must not be null or blank");
        }
        return new UserTokenRequest(username, password);
    }

    public String getGrantType() {
        return grantType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
