package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMUserCollectionAddSingle {
    public static final String SERIALIZED_NAME_ID = "id";
    @SerializedName(SERIALIZED_NAME_ID)
    @javax.annotation.Nullable
    private String id;

    public static final String SERIALIZED_NAME_DATA = "data";
    @SerializedName(SERIALIZED_NAME_DATA)
    @javax.annotation.Nonnull
    private String data;

    public static final String SERIALIZED_NAME_TYPE = "type";
    @SerializedName(SERIALIZED_NAME_TYPE)
    @javax.annotation.Nonnull
    private Integer type;

    public static final String SERIALIZED_NAME_EXT = "ext";
    @SerializedName(SERIALIZED_NAME_EXT)
    @javax.annotation.Nullable
    private String ext;

    public EMUserCollectionAddSingle() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public String getExt() { return ext; }
    public void setExt(String ext) { this.ext = ext; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionAddSingle that = (EMUserCollectionAddSingle) o;
        return Objects.equals(id, that.id) && Objects.equals(data, that.data) &&
               Objects.equals(type, that.type) && Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() { return Objects.hash(id, data, type, ext); }

    @Override
    public String toString() {
        return "class EMUserCollectionAddSingle {\n" +
               "    id: " + id + "\n" +
               "    data: " + data + "\n" +
               "    type: " + type + "\n" +
               "    ext: " + ext + "\n" +
               "}";
    }
}
