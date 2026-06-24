package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMUserCollectionExtModify {
    public static final String SERIALIZED_NAME_EXT = "ext";
    @SerializedName(SERIALIZED_NAME_EXT)
    @javax.annotation.Nonnull
    private String ext;

    public EMUserCollectionExtModify() {}

    public String getExt() { return ext; }
    public void setExt(String ext) { this.ext = ext; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionExtModify that = (EMUserCollectionExtModify) o;
        return Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() { return Objects.hash(ext); }

    @Override
    public String toString() {
        return "class EMUserCollectionExtModify {\n" +
               "    ext: " + ext + "\n" +
               "}";
    }
}
