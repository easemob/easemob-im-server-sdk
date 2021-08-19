package com.easemob.im.server.api.token.allocate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeTokenRequest implements TokenRequest {
    @SuppressWarnings("java:S1170")
    @JsonProperty("grant_type")
    private final String grantType = "agora";

    @JsonCreator
    public ExchangeTokenRequest() {
    }

    public static ExchangeTokenRequest getInstance() {
        return new ExchangeTokenRequest();
    }
}
