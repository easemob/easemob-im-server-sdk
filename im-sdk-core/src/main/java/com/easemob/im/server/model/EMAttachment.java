package com.easemob.im.server.model;

import java.util.Objects;

public class EMAttachment {
    private String id;
    private String url;
    private String secret;

    public EMAttachment(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public EMAttachment(String id, String url, String secret) {
        this.id = id;
        this.url = url;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EMAttachment that = (EMAttachment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, secret);
    }

    @Override
    public String toString() {
        return "EMAttachment{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
