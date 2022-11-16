package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.util.internal.StringUtil;

public class InheritTokenRequest implements TokenRequest {

    @SuppressWarnings("java:S1170")
    @JsonProperty("grant_type")
    private final String grantType = "inherit";

    @JsonProperty("username")
    private final String username;

    @JsonProperty("autoCreateUser")
    private final Boolean autoCreateUser;

    public InheritTokenRequest(String username, Boolean autoCreateUser) {
        this.username = username;
        this.autoCreateUser = autoCreateUser;
    }

    @JsonCreator
    public static InheritTokenRequest of(@JsonProperty("username") String username,
            @JsonProperty("autoCreateUser") Boolean autoCreateUser) {
        if (StringUtil.isNullOrEmpty(username)) {
            throw new EMInvalidArgumentException("username must not be null or blank");
        }
        return new InheritTokenRequest(username, autoCreateUser);
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
}
