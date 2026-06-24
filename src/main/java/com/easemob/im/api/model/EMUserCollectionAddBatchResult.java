package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EMUserCollectionAddBatchResult {
    public static final String SERIALIZED_NAME_COLLECTIONS = "collections";
    @SerializedName(SERIALIZED_NAME_COLLECTIONS)
    @javax.annotation.Nullable
    private List<EMCollectionResource> collections = new ArrayList<>();

    public EMUserCollectionAddBatchResult() {}

    public List<EMCollectionResource> getCollections() { return collections; }
    public void setCollections(List<EMCollectionResource> collections) { this.collections = collections; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionAddBatchResult that = (EMUserCollectionAddBatchResult) o;
        return Objects.equals(collections, that.collections);
    }

    @Override
    public int hashCode() { return Objects.hash(collections); }

    @Override
    public String toString() {
        return "class EMUserCollectionAddBatchResult {\n" +
               "    collections: " + collections + "\n" +
               "}";
    }
}
