package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.util.internal.StringUtil;

public class UserTokenTtlRequest implements TokenRequest {
    @SuppressWarnings("java:S1170")
    @JsonProperty("grant_type")
    private final String grantType = "password";

    @JsonProperty("username")
    private final String username;

    @JsonProperty("password")
    private final String password;

    @JsonProperty("ttl")
    private final Long ttl;

    public UserTokenTtlRequest(String username, String password, Long ttl) {
        this.username = username;
        this.password = password;
        this.ttl = ttl;
    }

    @JsonCreator
    public static UserTokenTtlRequest of(@JsonProperty("username") String username,
            @JsonProperty("password") String password, @JsonProperty("ttl") Long ttl) {
        if (StringUtil.isNullOrEmpty(username)) {
            throw new EMInvalidArgumentException("username must not be null or blank");
        }
        if (StringUtil.isNullOrEmpty(password)) {
            throw new EMInvalidArgumentException("password must not be null or blank");
        }
        return new UserTokenTtlRequest(username, password, ttl);
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

    public Long getTtl() {
        return ttl;
    }

}
