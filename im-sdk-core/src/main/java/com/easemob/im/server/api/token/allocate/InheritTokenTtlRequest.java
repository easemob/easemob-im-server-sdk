package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.util.internal.StringUtil;

public class InheritTokenTtlRequest implements TokenRequest {

    @SuppressWarnings("java:S1170")
    @JsonProperty("grant_type")
    private final String grantType = "inherit";

    @JsonProperty("username")
    private final String username;

    @JsonProperty("autoCreateUser")
    private final Boolean autoCreateUser;

    @JsonProperty("ttl")
    private final Long ttl;

    public InheritTokenTtlRequest(String username, Boolean autoCreateUser, Long ttl) {
        this.username = username;
        this.autoCreateUser = autoCreateUser;
        this.ttl = ttl;
    }

    @JsonCreator
    public static InheritTokenTtlRequest of(@JsonProperty("username") String username,
            @JsonProperty("autoCreateUser") Boolean autoCreateUser, @JsonProperty("ttl") Long ttl) {
        if (StringUtil.isNullOrEmpty(username)) {
            throw new EMInvalidArgumentException("username must not be null or blank");
        }
        return new InheritTokenTtlRequest(username, autoCreateUser, ttl);
    }

    public String getGrantType() {
        return grantType;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getAutoCreateUser() {
        return autoCreateUser;
    }

    public Long getTtl() {
        return ttl;
    }
}
