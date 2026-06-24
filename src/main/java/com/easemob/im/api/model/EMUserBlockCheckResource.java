package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * EMUserBlockCheckResource
 */
public class EMUserBlockCheckResource {
  public static final String SERIALIZED_NAME_USERNAME = "username";
  @SerializedName(SERIALIZED_NAME_USERNAME)
  @javax.annotation.Nullable
  private String username;

  public static final String SERIALIZED_NAME_RELATION = "relation";
  @SerializedName(SERIALIZED_NAME_RELATION)
  @javax.annotation.Nullable
  private String relation;

  public EMUserBlockCheckResource() {
  }

  @javax.annotation.Nullable
  public String getUsername() {
    return username;
  }

  public void setUsername(@javax.annotation.Nullable String username) {
    this.username = username;
  }

  /**
   * 是否在黑名单中：blacklist 是，not_blacklist 否
   */
  @javax.annotation.Nullable
  public String getRelation() {
    return relation;
  }

  public void setRelation(@javax.annotation.Nullable String relation) {
    this.relation = relation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EMUserBlockCheckResource that = (EMUserBlockCheckResource) o;
    return Objects.equals(username, that.username) && Objects.equals(relation, that.relation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, relation);
  }

  @Override
  public String toString() {
    return "class EMUserBlockCheckResource {\n" +
        "    username: " + username + "\n" +
        "    relation: " + relation + "\n" +
        "}";
  }
}
