package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTokenRequest implements TokenRequest {

    @JsonProperty("grant_type")
    private final String grantType = "password";

    @JsonProperty("username")
    private final String username;

    @JsonProperty("password")
    private final String password;

    public String getGrantType() {
        return grantType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @JsonCreator
    public static UserTokenRequest of(@JsonProperty("username") String username,
                                      @JsonProperty("password") String password) {
        if (username == null || username.isEmpty()) {
            throw new EMInvalidArgumentException("username must not be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new EMInvalidArgumentException("password must not be null or empty");
        }
        return new UserTokenRequest(username, password);
    }

    private UserTokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
