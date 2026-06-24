package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMCollectionItem {
    public static final String SERIALIZED_NAME_ID = "id";
    @SerializedName(SERIALIZED_NAME_ID)
    @javax.annotation.Nonnull
    private String id;

    public static final String SERIALIZED_NAME_TYPE = "type";
    @SerializedName(SERIALIZED_NAME_TYPE)
    @javax.annotation.Nonnull
    private Integer type;

    public static final String SERIALIZED_NAME_DATA = "data";
    @SerializedName(SERIALIZED_NAME_DATA)
    @javax.annotation.Nonnull
    private String data;

    public static final String SERIALIZED_NAME_EXT = "ext";
    @SerializedName(SERIALIZED_NAME_EXT)
    @javax.annotation.Nonnull
    private String ext;

    public static final String SERIALIZED_NAME_CREATED_AT = "createdAt";
    @SerializedName(SERIALIZED_NAME_CREATED_AT)
    @javax.annotation.Nonnull
    private Long createdAt;

    public EMCollectionItem() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getExt() { return ext; }
    public void setExt(String ext) { this.ext = ext; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMCollectionItem that = (EMCollectionItem) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) &&
               Objects.equals(data, that.data) && Objects.equals(ext, that.ext) &&
               Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() { return Objects.hash(id, type, data, ext, createdAt); }

    @Override
    public String toString() {
        return "class EMCollectionItem {\n" +
               "    id: " + id + "\n" +
               "    type: " + type + "\n" +
               "    data: " + data + "\n" +
               "    ext: " + ext + "\n" +
               "    createdAt: " + createdAt + "\n" +
               "}";
    }
}
