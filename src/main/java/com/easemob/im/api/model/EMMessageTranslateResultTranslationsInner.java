package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMMessageTranslateResultTranslationsInner {

    public static final String SERIALIZED_NAME_TEXT = "text";
    @SerializedName(SERIALIZED_NAME_TEXT)
    @javax.annotation.Nullable
    private String text;

    public static final String SERIALIZED_NAME_TO = "to";
    @SerializedName(SERIALIZED_NAME_TO)
    @javax.annotation.Nullable
    private String to;

    public EMMessageTranslateResultTranslationsInner() {}

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMMessageTranslateResultTranslationsInner that = (EMMessageTranslateResultTranslationsInner) o;
        return Objects.equals(text, that.text) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() { return Objects.hash(text, to); }

    @Override
    public String toString() {
        return "EMMessageTranslateResultTranslationsInner{text=" + text + ", to=" + to + "}";
    }
}
