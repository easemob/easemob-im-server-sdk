package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class ExchangeTokenResponse {

    private static final Logger log = LoggerFactory.getLogger(ExchangeTokenResponse.class);

    // easemob token
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expire_timestamp")
    private final Long expireEpochMilli;

    private ExchangeTokenResponse(String accessToken, Long expireEpochMilli) {
        this.accessToken = accessToken;
        this.expireEpochMilli = expireEpochMilli;
    }

    @JsonCreator
    public static ExchangeTokenResponse of(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expire_timestamp") Long expireEpochMilli) {
        return new ExchangeTokenResponse(accessToken, expireEpochMilli);
    }

    public Token asToken() {
        Instant expireAt = Instant.ofEpochMilli(expireEpochMilli);
        return new Token(this.accessToken, expireAt);
    }
}
