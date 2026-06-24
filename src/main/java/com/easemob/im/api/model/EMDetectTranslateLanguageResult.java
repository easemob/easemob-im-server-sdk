package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMDetectTranslateLanguageResult {

    public static final String SERIALIZED_NAME_LANGUAGE = "language";
    @SerializedName(SERIALIZED_NAME_LANGUAGE)
    @javax.annotation.Nullable
    private String language;

    public static final String SERIALIZED_NAME_SCORE = "score";
    @SerializedName(SERIALIZED_NAME_SCORE)
    @javax.annotation.Nullable
    private Double score;

    public static final String SERIALIZED_NAME_IS_TRANSLATION_SUPPORTED = "isTranslationSupported";
    @SerializedName(SERIALIZED_NAME_IS_TRANSLATION_SUPPORTED)
    @javax.annotation.Nullable
    private Boolean isTranslationSupported;

    public EMDetectTranslateLanguageResult() {}

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public Boolean getIsTranslationSupported() { return isTranslationSupported; }
    public void setIsTranslationSupported(Boolean isTranslationSupported) { this.isTranslationSupported = isTranslationSupported; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMDetectTranslateLanguageResult that = (EMDetectTranslateLanguageResult) o;
        return Objects.equals(language, that.language) && Objects.equals(score, that.score) && Objects.equals(isTranslationSupported, that.isTranslationSupported);
    }

    @Override
    public int hashCode() { return Objects.hash(language, score, isTranslationSupported); }

    @Override
    public String toString() {
        return "EMDetectTranslateLanguageResult{language=" + language + ", score=" + score + ", isTranslationSupported=" + isTranslationSupported + "}";
    }
}
