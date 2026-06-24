package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class EMUserCollectionDeleteResult {
    public static final String SERIALIZED_NAME_RESULT = "result";
    @SerializedName(SERIALIZED_NAME_RESULT)
    @javax.annotation.Nullable
    private Boolean result;

    public EMUserCollectionDeleteResult() {}

    public Boolean getResult() { return result; }
    public void setResult(Boolean result) { this.result = result; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMUserCollectionDeleteResult that = (EMUserCollectionDeleteResult) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() { return Objects.hash(result); }

    @Override
    public String toString() {
        return "class EMUserCollectionDeleteResult {\n" +
               "    result: " + result + "\n" +
               "}";
    }
}
