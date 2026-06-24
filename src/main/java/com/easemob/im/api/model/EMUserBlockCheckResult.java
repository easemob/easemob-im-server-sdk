package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * EMUserBlockCheckResult
 */
public class EMUserBlockCheckResult {
  public static final String SERIALIZED_NAME_ENTITIES = "entities";
  @SerializedName(SERIALIZED_NAME_ENTITIES)
  @javax.annotation.Nullable
  private List<EMUserBlockCheckResource> entities = new ArrayList<>();

  public EMUserBlockCheckResult() {
  }

  public EMUserBlockCheckResult addEntitiesItem(EMUserBlockCheckResource item) {
    if (this.entities == null) {
      this.entities = new ArrayList<>();
    }
    this.entities.add(item);
    return this;
  }

  @javax.annotation.Nullable
  public List<EMUserBlockCheckResource> getEntities() {
    return entities;
  }

  public void setEntities(@javax.annotation.Nullable List<EMUserBlockCheckResource> entities) {
    this.entities = entities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EMUserBlockCheckResult that = (EMUserBlockCheckResult) o;
    return Objects.equals(entities, that.entities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entities);
  }

  @Override
  public String toString() {
    return "class EMUserBlockCheckResult {\n" +
        "    entities: " + entities + "\n" +
        "}";
  }
}
