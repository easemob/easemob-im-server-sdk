package com.easemob.im.server.model;

import java.net.URI;

@SuppressWarnings("java:S2160")
public class EMImageMessage extends EMMessage {
    private String displayName;

    private String secret;

    private URI uri;

    private int bytes;

    private Double width;

    private Double height;

    public EMImageMessage() {
        super(MessageType.IMAGE);
    }

    public URI uri() {
        return this.uri;
    }

    public EMImageMessage uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public EMImageMessage displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String displayName() {
        return this.displayName;
    }

    public EMImageMessage secret(String secret) {
        this.secret = secret;
        return this;
    }

    public String secret() {
        return this.secret;
    }

    public Integer bytes() {
        return this.bytes;
    }

    public EMImageMessage bytes(int bytes) {
        this.bytes = bytes;
        return this;
    }

    public Double width() {
        return this.width;
    }

    public EMImageMessage width(double pixels) {
        this.width = pixels;
        return this;
    }

    public Double height() {
        return this.height;
    }

    public EMImageMessage height(double pixels) {
        this.height = pixels;
        return this;
    }

    @Override
    public String toString() {
        return "EMImageMessage{" +
                "displayName='" + displayName + '\'' +
                ", secret='" + secret + '\'' +
                ", uri=" + uri +
                ", bytes=" + bytes +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
