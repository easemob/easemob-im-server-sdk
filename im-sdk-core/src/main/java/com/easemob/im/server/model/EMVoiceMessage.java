package com.easemob.im.server.model;

import java.net.URI;

@SuppressWarnings("java:S2160")
public class EMVoiceMessage extends EMMessage {

    private URI uri;

    private String displayName;

    private String secret;

    private Integer bytes;

    /** the time unit depends on usage **/
    private Integer duration;

    public EMVoiceMessage() {
        super(MessageType.AUDIO);
    }

    public URI uri() {
        return this.uri;
    }

    public EMVoiceMessage uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public String displayName() {
        return this.displayName;
    }

    public EMVoiceMessage displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String secret() {
        return this.secret;
    }

    public EMVoiceMessage secret(String secret) {
        this.secret = secret;
        return this;
    }

    public Integer bytes() {
        return this.bytes;
    }

    public EMVoiceMessage bytes(int bytes) {
        this.bytes = bytes;
        return this;
    }

    public Integer duration() {
        return this.duration;
    }

    public EMVoiceMessage duration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public String toString() {
        return "EMVoiceMessage{" +
                "uri=" + uri +
                ", displayName='" + displayName + '\'' +
                ", secret='" + secret + '\'' +
                ", bytes=" + bytes +
                ", duration=" + duration +
                '}';
    }
}

