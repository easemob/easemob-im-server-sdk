package com.easemob.im.server.model;

import java.util.*;

public class EMCustomMessage extends EMMessage {
    private String customEvent;
    private Set<EMKeyValue> customExtensions;

    public EMCustomMessage() {
        super(MessageType.CUSTOM);
    }

    public String customEvent() {
        return this.customEvent;
    }

    public EMCustomMessage customEvent(String customEvent) {
        this.customEvent = customEvent;
        return this;
    }

    public Set<EMKeyValue> customExtensions() {
        return this.customExtensions;
    }

    public EMCustomMessage customExtensions(Set<EMKeyValue> customParams) {
        this.customExtensions = customParams;
        return this;
    }

    public EMKeyValue customExtension(String key) {
        if (this.customExtensions == null) {
            return null;
        }
        for (EMKeyValue keyValue : this.customExtensions) {
            if (keyValue.key().equals(key)) {
                return keyValue;
            }
        }
        return null;
    }

    public EMCustomMessage customExtension(String key, boolean value) {
        if (this.customExtensions == null) {
            this.customExtensions = new HashSet<>();
        }
        this.customExtensions.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCustomMessage customExtension(String key, int value) {
        if (this.customExtensions == null) {
            this.customExtensions = new HashSet<>();
        }
        this.customExtensions.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCustomMessage customExtension(String key, long value) {
        if (this.customExtensions == null) {
            this.customExtensions = new HashSet<>();
        }
        this.customExtensions.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCustomMessage customExtension(String key, float value) {
        if (this.customExtensions == null) {
            this.customExtensions = new HashSet<>();
        }
        this.customExtensions.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCustomMessage customExtension(String key, double value) {
        if (this.customExtensions == null) {
            this.customExtensions = new HashSet<>();
        }
        this.customExtensions.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCustomMessage customExtension(String key, String value) {
        if (this.customExtensions == null) {
            this.customExtensions = new HashSet<>();
        }
        this.customExtensions.add(EMKeyValue.of(key, value));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        EMCustomMessage that = (EMCustomMessage) o;
        return Objects.equals(customEvent, that.customEvent) && Objects
                .equals(customExtensions, that.customExtensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customEvent, customExtensions);
    }

    @Override
    public String toString() {
        return "EMCustomMessage{" +
                "customEvent='" + customEvent + '\'' +
                ", customParams=" + customExtensions +
                '}';
    }
}
