package com.easemob.im.server.api.token;

import java.time.Instant;

public class Token {

    private String value;

    private Instant expireAt;

    public Token(String value, Instant expireAt) {
        this.value = value;
        this.expireAt = expireAt;
    }

    public boolean isValid() {
        return Instant.now().isBefore(this.expireAt);
    }

    public String getValue() {
        return this.value;
    }

    public Instant getExpireTimestamp() {
        return this.expireAt;
    }

}
