package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMTranslateSupportLanguage {

    public static final String SERIALIZED_NAME_CODE = "code";
    @SerializedName(SERIALIZED_NAME_CODE)
    @javax.annotation.Nullable
    private String code;

    public static final String SERIALIZED_NAME_NAME = "name";
    @SerializedName(SERIALIZED_NAME_NAME)
    @javax.annotation.Nullable
    private String name;

    public static final String SERIALIZED_NAME_NATIVE_NAME = "nativeName";
    @SerializedName(SERIALIZED_NAME_NATIVE_NAME)
    @javax.annotation.Nullable
    private String nativeName;

    public EMTranslateSupportLanguage() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNativeName() { return nativeName; }
    public void setNativeName(String nativeName) { this.nativeName = nativeName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMTranslateSupportLanguage that = (EMTranslateSupportLanguage) o;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(nativeName, that.nativeName);
    }

    @Override
    public int hashCode() { return Objects.hash(code, name, nativeName); }

    @Override
    public String toString() {
        return "EMTranslateSupportLanguage{code=" + code + ", name=" + name + ", nativeName=" + nativeName + "}";
    }
}
