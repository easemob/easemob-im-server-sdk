package com.easemob.im.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * EMUserBlockCheck
 */
public class EMUserBlockCheck {
  public static final String SERIALIZED_NAME_USERNAME = "username";
  @SerializedName(SERIALIZED_NAME_USERNAME)
  @javax.annotation.Nonnull
  private String username;

  public static final String SERIALIZED_NAME_CHECK_LIST = "check_list";
  @SerializedName(SERIALIZED_NAME_CHECK_LIST)
  @javax.annotation.Nonnull
  private List<String> checkList = new ArrayList<>();

  public EMUserBlockCheck() {
  }

  public EMUserBlockCheck username(@javax.annotation.Nonnull String username) {
    this.username = username;
    return this;
  }

  @javax.annotation.Nonnull
  public String getUsername() {
    return username;
  }

  public void setUsername(@javax.annotation.Nonnull String username) {
    this.username = username;
  }

  public EMUserBlockCheck checkList(@javax.annotation.Nonnull List<String> checkList) {
    this.checkList = checkList;
    return this;
  }

  public EMUserBlockCheck addCheckListItem(String checkListItem) {
    if (this.checkList == null) {
      this.checkList = new ArrayList<>();
    }
    this.checkList.add(checkListItem);
    return this;
  }

  @javax.annotation.Nonnull
  public List<String> getCheckList() {
    return checkList;
  }

  public void setCheckList(@javax.annotation.Nonnull List<String> checkList) {
    this.checkList = checkList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EMUserBlockCheck that = (EMUserBlockCheck) o;
    return Objects.equals(username, that.username) && Objects.equals(checkList, that.checkList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, checkList);
  }

  @Override
  public String toString() {
    return "class EMUserBlockCheck {\n" +
        "    username: " + username + "\n" +
        "    checkList: " + checkList + "\n" +
        "}";
  }
}
