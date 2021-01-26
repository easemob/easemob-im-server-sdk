package com.easemob.im.server.auth.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;

public class TokenResponse {

    private final String accessToken;

    private final int expiresIn;

    @JsonCreator
    public static TokenResponse of(@JsonProperty("access_token") String accessToken,
                                   @JsonProperty("expires_in") int expiresIn) {
        return new TokenResponse(accessToken, expiresIn);
    }

    private TokenResponse(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public Token asToken() {
        Instant expireAt = Instant.now().plus(Duration.ofSeconds(this.expiresIn));
        return new Token(this.accessToken, expireAt);
    }

}
