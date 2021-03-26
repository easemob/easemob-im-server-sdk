package com.easemob.im.server.model;

import java.net.URI;

@SuppressWarnings("java:S2160")
public class EMFileMessage extends EMMessage {

    private URI uri;

    private String displayName;

    private String secret;

    private Integer bytes;

    public EMFileMessage() {
        super(MessageType.FILE);
    }

    public URI uri() {
        return this.uri;
    }

    public EMFileMessage uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public String displayName() {
        return this.displayName;
    }

    public EMFileMessage displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String secret() {
        return this.secret;
    }

    public EMFileMessage secret(String secret) {
        this.secret = secret;
        return this;
    }

    public Integer bytes() {
        return this.bytes;
    }

    public EMFileMessage bytes(int bytes) {
        this.bytes = bytes;
        return this;
    }

    @Override
    public String toString() {
        return "EMFileMessage{" +
                "uri=" + uri +
                ", displayName='" + displayName + '\'' +
                ", secret='" + secret + '\'' +
                ", bytes=" + bytes +
                '}';
    }
}
