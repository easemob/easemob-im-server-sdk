package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EMUserCollectionDelete {
    public static final String SERIALIZED_NAME_COLLECTION_IDS = "collection_ids";
    @SerializedName(SERIALIZED_NAME_COLLECTION_IDS)
    @javax.annotation.Nonnull
    private List<String> collectionIds = new ArrayList<>();

    public EMUserCollectionDelete() {}

    public List<String> getCollectionIds() { return collectionIds; }
    public void setCollectionIds(List<String> collectionIds) { this.collectionIds = collectionIds; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionDelete that = (EMUserCollectionDelete) o;
        return Objects.equals(collectionIds, that.collectionIds);
    }

    @Override
    public int hashCode() { return Objects.hash(collectionIds); }

    @Override
    public String toString() {
        return "class EMUserCollectionDelete {\n" +
               "    collectionIds: " + collectionIds + "\n" +
               "}";
    }
}
