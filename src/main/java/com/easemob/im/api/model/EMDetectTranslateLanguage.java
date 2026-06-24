package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMDetectTranslateLanguage {

    public static final String SERIALIZED_NAME_TEXT = "text";
    @SerializedName(SERIALIZED_NAME_TEXT)
    @javax.annotation.Nonnull
    private String text;

    public EMDetectTranslateLanguage() {}

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMDetectTranslateLanguage that = (EMDetectTranslateLanguage) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() { return Objects.hash(text); }

    @Override
    public String toString() { return "EMDetectTranslateLanguage{text=" + text + "}"; }
}
