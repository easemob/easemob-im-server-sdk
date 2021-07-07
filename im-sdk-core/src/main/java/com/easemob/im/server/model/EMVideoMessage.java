package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

@SuppressWarnings("java:S2160")
public class EMVideoMessage extends EMMessage {
    private URI uri;

    private String displayName;

    private String secret;

    private Integer bytes;
    /**
     * the time unit depends on usage
     **/
    private Integer duration;

    private String thumb;

    @JsonProperty("thumb_secret")
    private String thumbSecret;

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

    public String thumb() {
        return this.thumb;
    }

    public EMVideoMessage thumb(String thumb) {
        this.thumb = thumb;
        return this;
    }

    public String thumbSecret() {
        return this.thumbSecret;
    }

    public EMVideoMessage thumbSecret(String thumbSecret) {
        this.thumbSecret = thumbSecret;
        return this;
    }

    @Override
    public String toString() {
        return "EMVideoMessage{" +
                "uri=" + uri +
                ", displayName='" + displayName + '\'' +
                ", secret='" + secret + '\'' +
                ", bytes=" + bytes +
                ", duration=" + duration +
                ", thumb='" + thumb + '\'' +
                ", thumbSecret='" + thumbSecret + '\'' +
                '}';
    }
}
