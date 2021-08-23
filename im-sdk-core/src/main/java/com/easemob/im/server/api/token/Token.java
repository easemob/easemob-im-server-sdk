package com.easemob.im.server.api.token;

import com.easemob.im.server.api.util.Utilities;

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

    public Instant getExpireAt() {
        return this.expireAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + Utilities.mask(value) + '\'' +
                ", expireAt=" + expireAt +
                '}';
    }
}
