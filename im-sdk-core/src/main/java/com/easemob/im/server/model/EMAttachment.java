package com.easemob.im.server.model;

import java.util.Objects;

public class EMAttachment {
    private String id;
    private String secret;

    public EMAttachment(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMAttachment that = (EMAttachment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secret);
    }

    @Override
    public String toString() {
        return "EMAttachment{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
