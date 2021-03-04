package com.easemob.im.server.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class EMBlock {

    private String username;

    private Optional<Instant> expireAt;

    public static EMBlock user(String username) {
        return new EMBlock(username, Optional.empty());
    }

    public static EMBlock user(String username, Instant expireAt) {
        return new EMBlock(username, Optional.of(expireAt));
    }

    private EMBlock(String username, Optional<Instant> expireAt) {
        this.username = username;
        this.expireAt = expireAt;
    }

    public String getUsername() {
        return this.username;
    }

    public Optional<Instant> getExpireAt() {
        return this.expireAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMBlock emBlock = (EMBlock) o;
        return Objects.equals(username, emBlock.username) && Objects.equals(expireAt, emBlock.expireAt);
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
