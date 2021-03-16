package com.easemob.im.server.model;

import java.net.URI;
import java.util.Objects;

public class EMVideoMessage extends EMMessage {
    private URI uri;

    private String displayName;

    private String secret;

    private Integer bytes;
    /** the time unit depends on usage **/
    private Integer duration;

    public EMVideoMessage() {
        super(MessageType.VIDEO);
    }

    public URI uri() {
        return this.uri;
    }

    public EMVideoMessage uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public String displayName() {
        return this.displayName;
    }

    public EMVideoMessage displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String secret() {
        return this.secret;
    }

    public EMVideoMessage secret(String secret) {
        this.secret = secret;
        return this;
    }

    public Integer bytes() {
        return this.bytes;
    }

    public EMVideoMessage bytes(int bytes) {
        this.bytes = bytes;
        return this;
    }

    public Integer duration() {
        return this.duration;
    }

    public EMVideoMessage duration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EMVideoMessage that = (EMVideoMessage) o;
        return Objects.equals(uri, that.uri) && Objects.equals(displayName, that.displayName) && Objects.equals(secret, that.secret) && Objects.equals(bytes, that.bytes) && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uri, displayName, secret, bytes, duration);
    }

    @Override
    public String toString() {
        return "EMVideoMessage{" +
                "uri=" + uri +
                ", displayName='" + displayName + '\'' +
                ", secret='" + secret + '\'' +
                ", bytes=" + bytes +
                ", duration=" + duration +
                '}';
    }
}
