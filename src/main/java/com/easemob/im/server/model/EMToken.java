package com.easemob.im.server.model;

import java.time.Instant;

public class EMToken {

    private String value;

    private Instant expireAt;

    public EMToken(String value, Instant expireAt) {
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
