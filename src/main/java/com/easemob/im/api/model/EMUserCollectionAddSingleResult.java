package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMUserCollectionAddSingleResult {
    public static final String SERIALIZED_NAME_COLLECTION = "collection";
    @SerializedName(SERIALIZED_NAME_COLLECTION)
    @javax.annotation.Nullable
    private EMCollectionResource collection;

    public EMUserCollectionAddSingleResult() {}

    public EMCollectionResource getCollection() { return collection; }
    public void setCollection(EMCollectionResource collection) { this.collection = collection; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionAddSingleResult that = (EMUserCollectionAddSingleResult) o;
        return Objects.equals(collection, that.collection);
    }

    @Override
    public int hashCode() { return Objects.hash(collection); }

    @Override
    public String toString() {
        return "class EMUserCollectionAddSingleResult {\n" +
               "    collection: " + collection + "\n" +
               "}";
    }
}
