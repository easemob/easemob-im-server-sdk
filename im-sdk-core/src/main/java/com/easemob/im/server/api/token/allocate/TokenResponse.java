package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

public class TokenResponse {

    private static final Logger log = LoggerFactory.getLogger(TokenResponse.class);

    // easemob token
    @JsonProperty("access_token")
    private final String accessToken;

    // TTL in seconds
    @JsonProperty("expires_in")
    private final int expireInSeconds;

    private TokenResponse(String accessToken, int expireInSeconds) {
        this.accessToken = accessToken;
        this.expireInSeconds = expireInSeconds;
    }

    @JsonCreator
    public static TokenResponse of(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expireInSeconds) {
        return new TokenResponse(accessToken, expireInSeconds);
    }

    public Token asToken() {
        Instant expireAt = Instant.now().plus(Duration.ofSeconds(this.expireInSeconds));
        return new Token(this.accessToken, expireAt);
    }
}
