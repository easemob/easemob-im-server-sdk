package com.easemob.im.server.model;

import java.util.Objects;

public class EMTextMessage extends EMMessage {

    private String text;

    public EMTextMessage() {
        super(MessageType.TEXT);
    }

    public String text() {
        return this.text;
    }

    public EMTextMessage text(String text) {
        this.text = text;
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
        EMTextMessage that = (EMTextMessage) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "EMTextMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
