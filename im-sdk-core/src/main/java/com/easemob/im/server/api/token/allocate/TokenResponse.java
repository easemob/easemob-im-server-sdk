package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;

public class TokenResponse {

    @JsonProperty("access_token")
    private final String accessToken;

    // this can be TTL in seconds OR epochMilli
    @JsonProperty("expires_in")
    private final long expiresIn;

    private TokenResponse(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    @JsonCreator
    public static TokenResponse of(@JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") long expiresIn) {
        return new TokenResponse(accessToken, expiresIn);
    }

    public Token asTokenWithEpochMilli() {
        long expireEpochMilli = this.expiresIn;
        Instant expireAt = Instant.ofEpochMilli(expireEpochMilli);
        return new Token(this.accessToken, expireAt);
    }

    public Token asToken() {
        Instant expireAt = Instant.now().plus(Duration.ofSeconds(this.expiresIn));
        return new Token(this.accessToken, expireAt);
    }
}
