package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMCollectionResource {
    public static final String SERIALIZED_NAME_ID = "id";
    @SerializedName(SERIALIZED_NAME_ID)
    @javax.annotation.Nullable
    private String id;

    public static final String SERIALIZED_NAME_TYPE = "type";
    @SerializedName(SERIALIZED_NAME_TYPE)
    @javax.annotation.Nullable
    private Integer type;

    public static final String SERIALIZED_NAME_DATA = "data";
    @SerializedName(SERIALIZED_NAME_DATA)
    @javax.annotation.Nullable
    private String data;

    public static final String SERIALIZED_NAME_EXT = "ext";
    @SerializedName(SERIALIZED_NAME_EXT)
    @javax.annotation.Nullable
    private String ext;

    public static final String SERIALIZED_NAME_CREATED_AT = "createdAt";
    @SerializedName(SERIALIZED_NAME_CREATED_AT)
    @javax.annotation.Nullable
    private Long createdAt;

    public static final String SERIALIZED_NAME_UPDATED_AT = "updatedAt";
    @SerializedName(SERIALIZED_NAME_UPDATED_AT)
    @javax.annotation.Nullable
    private Long updatedAt;

    public EMCollectionResource() {}

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

    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMCollectionResource that = (EMCollectionResource) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) &&
               Objects.equals(data, that.data) && Objects.equals(ext, that.ext) &&
               Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() { return Objects.hash(id, type, data, ext, createdAt, updatedAt); }

    @Override
    public String toString() {
        return "class EMCollectionResource {\n" +
               "    id: " + id + "\n" +
               "    type: " + type + "\n" +
               "    data: " + data + "\n" +
               "    ext: " + ext + "\n" +
               "    createdAt: " + createdAt + "\n" +
               "    updatedAt: " + updatedAt + "\n" +
               "}";
    }
}
