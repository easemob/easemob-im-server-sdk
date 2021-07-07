package com.easemob.im.server.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EMCommandMessage extends EMMessage {
    private String action;

    private Set<EMKeyValue> params;

    public EMCommandMessage() {
        super(MessageType.COMMAND);
    }

    public String action() {
        return this.action;
    }

    public EMCommandMessage action(String action) {
        this.action = action;
        return this;
    }

    public Set<EMKeyValue> params() {
        return this.params;
    }

    public EMCommandMessage params(Set<EMKeyValue> params) {
        this.params = params;
        return this;
    }

    public EMCommandMessage param(String key, boolean value) {
        if (this.params == null) {
            this.params = new HashSet<>();
        }
        this.params.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCommandMessage param(String key, int value) {
        if (this.params == null) {
            this.params = new HashSet<>();
        }
        this.params.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCommandMessage param(String key, long value) {
        if (this.params == null) {
            this.params = new HashSet<>();
        }
        this.params.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCommandMessage param(String key, float value) {
        if (this.params == null) {
            this.params = new HashSet<>();
        }
        this.params.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCommandMessage param(String key, double value) {
        if (this.params == null) {
            this.params = new HashSet<>();
        }
        this.params.add(EMKeyValue.of(key, value));
        return this;
    }

    public EMCommandMessage param(String key, String value) {
        if (this.params == null) {
            this.params = new HashSet<>();
        }
        this.params.add(EMKeyValue.of(key, value));
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
        EMCommandMessage that = (EMCommandMessage) o;
        return Objects.equals(action, that.action) && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), action, params);
    }

    @Override
    public String toString() {
        return "EMCommandMessage{" +
                "action='" + action + '\'' +
                ", params=" + params +
                '}';
    }
}
