package com.easemob.im.server.model;

import java.time.Instant;
import java.util.Objects;

public class EMBlock {

    private String username;

    private Instant expireAt;

    public EMBlock(String username, Instant expireAt) {
        this.username = username;
        this.expireAt = expireAt;
    }

    public String getUsername() {
        return this.username;
    }

    public Instant getExpireAt() {
        return this.expireAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EMBlock emBlock = (EMBlock) o;
        return Objects.equals(username, emBlock.username) && Objects
                .equals(expireAt, emBlock.expireAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, expireAt);
    }

    @Override
    public String toString() {
        return "EMBlock{" +
                "username='" + username + '\'' +
                ", expireAt=" + expireAt +
                '}';
    }
}
