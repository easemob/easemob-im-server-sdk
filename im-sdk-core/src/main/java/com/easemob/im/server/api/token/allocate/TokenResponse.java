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

    @JsonProperty("access_token")
    private final String accessToken;

    // epochInMilli
    private final long expireEpochMilli;

    // TTL in seconds
    private final long expireInSeconds;

    private TokenResponse(String accessToken, Long expiresInSeconds, Long expireEpochMilli) {
        this.accessToken = accessToken;
        this.expireInSeconds = expiresInSeconds == null ? -1 : expiresInSeconds;
        this.expireEpochMilli = expireEpochMilli == null ? -1 : expireEpochMilli;
    }

    @JsonCreator
    public static TokenResponse of(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") Long expireInSeconds,
            @JsonProperty("expires_timestamp") Long expireEpochMilli) {
        TokenResponse tokenResponse = new TokenResponse(accessToken, expireInSeconds, expireEpochMilli);
        log.debug("tokenResponse = {}", tokenResponse);
        return tokenResponse;
    }

    public Token asToken() {
        Instant expireAt = Instant.now().plus(Duration.ofSeconds(this.expireInSeconds));
        return new Token(this.accessToken, expireAt);
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", expireEpochMilli=" + expireEpochMilli +
                ", expireInSeconds=" + expireInSeconds +
                '}';
    }

}
