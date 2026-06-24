package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class EMMessageTranslate {

    public static final String SERIALIZED_NAME_FROM = "from";
    @SerializedName(SERIALIZED_NAME_FROM)
    @javax.annotation.Nullable
    private String from;

    public static final String SERIALIZED_NAME_TEXT = "text";
    @SerializedName(SERIALIZED_NAME_TEXT)
    @javax.annotation.Nonnull
    private String text;

    public static final String SERIALIZED_NAME_TO = "to";
    @SerializedName(SERIALIZED_NAME_TO)
    @javax.annotation.Nonnull
    private List<String> to;

    public EMMessageTranslate() {}

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getTo() { return to; }
    public void setTo(List<String> to) { this.to = to; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMMessageTranslate that = (EMMessageTranslate) o;
        return Objects.equals(from, that.from) && Objects.equals(text, that.text) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() { return Objects.hash(from, text, to); }

    @Override
    public String toString() {
        return "EMMessageTranslate{from=" + from + ", text=" + text + ", to=" + to + "}";
    }
}
