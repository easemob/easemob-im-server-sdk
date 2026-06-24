package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EMUserCollectionAddBatch {
    public static final String SERIALIZED_NAME_COLLECTIONS = "collections";
    @SerializedName(SERIALIZED_NAME_COLLECTIONS)
    @javax.annotation.Nonnull
    private List<EMCollectionItem> collections = new ArrayList<>();

    public static final String SERIALIZED_NAME_USERNAME = "username";
    @SerializedName(SERIALIZED_NAME_USERNAME)
    @javax.annotation.Nonnull
    private String username;

    public EMUserCollectionAddBatch() {}

    public List<EMCollectionItem> getCollections() { return collections; }
    public void setCollections(List<EMCollectionItem> collections) { this.collections = collections; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionAddBatch that = (EMUserCollectionAddBatch) o;
        return Objects.equals(collections, that.collections) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() { return Objects.hash(collections, username); }

    @Override
    public String toString() {
        return "class EMUserCollectionAddBatch {\n" +
               "    collections: " + collections + "\n" +
               "    username: " + username + "\n" +
               "}";
    }
}
