package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class EMMessageTranslateResult {

    public static final String SERIALIZED_NAME_TRANSLATIONS = "translations";
    @SerializedName(SERIALIZED_NAME_TRANSLATIONS)
    @javax.annotation.Nullable
    private List<EMMessageTranslateResultTranslationsInner> translations;

    public EMMessageTranslateResult() {}

    public List<EMMessageTranslateResultTranslationsInner> getTranslations() { return translations; }
    public void setTranslations(List<EMMessageTranslateResultTranslationsInner> translations) { this.translations = translations; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMMessageTranslateResult that = (EMMessageTranslateResult) o;
        return Objects.equals(translations, that.translations);
    }

    @Override
    public int hashCode() { return Objects.hash(translations); }

    @Override
    public String toString() {
        return "EMMessageTranslateResult{translations=" + translations + "}";
    }
}
