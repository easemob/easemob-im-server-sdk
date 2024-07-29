/*
 * EMService
 * Easemob Rest API
 *
 * The version of the OpenAPI document: 1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.easemob.im.api.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.easemob.im.JSON;

/**
 * EMModifyGroup
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-07-29T15:02:31.237631+08:00[Asia/Shanghai]")
public class EMModifyGroup {
  public static final String SERIALIZED_NAME_GROUPNAME = "groupname";
  @SerializedName(SERIALIZED_NAME_GROUPNAME)
  private String groupname;

  public static final String SERIALIZED_NAME_AVATAR = "avatar";
  @SerializedName(SERIALIZED_NAME_AVATAR)
  private String avatar;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_PUBLIC = "public";
  @SerializedName(SERIALIZED_NAME_PUBLIC)
  private Boolean _public;

  public static final String SERIALIZED_NAME_MAXUSERS = "maxusers";
  @SerializedName(SERIALIZED_NAME_MAXUSERS)
  private Integer maxusers;

  public static final String SERIALIZED_NAME_ALLOWINVITES = "allowinvites";
  @SerializedName(SERIALIZED_NAME_ALLOWINVITES)
  private Boolean allowinvites;

  public static final String SERIALIZED_NAME_MEMBERSONLY = "membersonly";
  @SerializedName(SERIALIZED_NAME_MEMBERSONLY)
  private Boolean membersonly;

  public static final String SERIALIZED_NAME_INVITE_NEED_CONFIRM = "invite_need_confirm";
  @SerializedName(SERIALIZED_NAME_INVITE_NEED_CONFIRM)
  private Boolean inviteNeedConfirm;

  public static final String SERIALIZED_NAME_CUSTOM = "custom";
  @SerializedName(SERIALIZED_NAME_CUSTOM)
  private String custom;

  public static final String SERIALIZED_NAME_NEWOWNER = "newowner";
  @SerializedName(SERIALIZED_NAME_NEWOWNER)
  private String newowner;

  public EMModifyGroup() {
  }

  public EMModifyGroup groupname(String groupname) {

    this.groupname = groupname;
    return this;
  }

  /**
   * 群组名称，最大长度为 128 字符
   * @return groupname
   **/
  @javax.annotation.Nullable
  public String getGroupname() {
    return groupname;
  }


  public void setGroupname(String groupname) {
    this.groupname = groupname;
  }


  public EMModifyGroup avatar(String avatar) {

    this.avatar = avatar;
    return this;
  }

  /**
   * 群组头像的 URL，最大长度为 1024 字符
   * @return avatar
   **/
  @javax.annotation.Nullable
  public String getAvatar() {
    return avatar;
  }


  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public EMModifyGroup description(String description) {

    this.description = description;
    return this;
  }

  /**
   * 群组描述，最大长度为 512 字符
   * @return description
   **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public EMModifyGroup _public(Boolean _public) {

    this._public = _public;
    return this;
  }

  /**
   * 是否是公开群。 - true：公开群； - false：私有群
   * @return _public
   **/
  @javax.annotation.Nullable
  public Boolean getPublic() {
    return _public;
  }


  public void setPublic(Boolean _public) {
    this._public = _public;
  }


  public EMModifyGroup maxusers(Integer maxusers) {

    this.maxusers = maxusers;
    return this;
  }

  /**
   * 群组最大成员数（包括群主）。对于普通群，该参数的默认值为 200，大型群为 1000。不同套餐支持的人数上限不同，详见 产品价格
   * @return maxusers
   **/
  @javax.annotation.Nullable
  public Integer getMaxusers() {
    return maxusers;
  }


  public void setMaxusers(Integer maxusers) {
    this.maxusers = maxusers;
  }


  public EMModifyGroup allowinvites(Boolean allowinvites) {

    this.allowinvites = allowinvites;
    return this;
  }

  /**
   * 是否允许群成员邀请用户加入群组： - true：群成员可拉人入群; - （默认）false：只有群主或者管理员才可以拉人入群。 注：该参数仅对私有群有效，因为公开群不允许群成员邀请其他用户入群
   * @return allowinvites
   **/
  @javax.annotation.Nullable
  public Boolean getAllowinvites() {
    return allowinvites;
  }


  public void setAllowinvites(Boolean allowinvites) {
    this.allowinvites = allowinvites;
  }


  public EMModifyGroup membersonly(Boolean membersonly) {

    this.membersonly = membersonly;
    return this;
  }

  /**
   * 加入群组是否需要群主或者群管理员审批： - true：是； - false：否
   * @return membersonly
   **/
  @javax.annotation.Nullable
  public Boolean getMembersonly() {
    return membersonly;
  }


  public void setMembersonly(Boolean membersonly) {
    this.membersonly = membersonly;
  }


  public EMModifyGroup inviteNeedConfirm(Boolean inviteNeedConfirm) {

    this.inviteNeedConfirm = inviteNeedConfirm;
    return this;
  }

  /**
   * 邀请用户入群时是否需要被邀用户同意。 - （默认）true：是； - false：否
   * @return inviteNeedConfirm
   **/
  @javax.annotation.Nullable
  public Boolean getInviteNeedConfirm() {
    return inviteNeedConfirm;
  }


  public void setInviteNeedConfirm(Boolean inviteNeedConfirm) {
    this.inviteNeedConfirm = inviteNeedConfirm;
  }


  public EMModifyGroup custom(String custom) {

    this.custom = custom;
    return this;
  }

  /**
   * 群组扩展信息，例如可以给群组添加业务相关的标记，不要超过 1,024 字符
   * @return custom
   **/
  @javax.annotation.Nullable
  public String getCustom() {
    return custom;
  }


  public void setCustom(String custom) {
    this.custom = custom;
  }


  public EMModifyGroup newowner(String newowner) {

    this.newowner = newowner;
    return this;
  }

  /**
   * 群组的新管理员的用户 ID
   * @return newowner
   **/
  @javax.annotation.Nullable
  public String getNewowner() {
    return newowner;
  }


  public void setNewowner(String newowner) {
    this.newowner = newowner;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMModifyGroup modifyGroup = (EMModifyGroup) o;
    return Objects.equals(this.groupname, modifyGroup.groupname) &&
            Objects.equals(this.avatar, modifyGroup.avatar) &&
            Objects.equals(this.description, modifyGroup.description) &&
            Objects.equals(this._public, modifyGroup._public) &&
            Objects.equals(this.maxusers, modifyGroup.maxusers) &&
            Objects.equals(this.allowinvites, modifyGroup.allowinvites) &&
            Objects.equals(this.membersonly, modifyGroup.membersonly) &&
            Objects.equals(this.inviteNeedConfirm, modifyGroup.inviteNeedConfirm) &&
            Objects.equals(this.custom, modifyGroup.custom) &&
            Objects.equals(this.newowner, modifyGroup.newowner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupname, avatar, description, _public, maxusers, allowinvites, membersonly, inviteNeedConfirm, custom, newowner);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMModifyGroup {\n");
    sb.append("    groupname: ").append(toIndentedString(groupname)).append("\n");
    sb.append("    avatar: ").append(toIndentedString(avatar)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    _public: ").append(toIndentedString(_public)).append("\n");
    sb.append("    maxusers: ").append(toIndentedString(maxusers)).append("\n");
    sb.append("    allowinvites: ").append(toIndentedString(allowinvites)).append("\n");
    sb.append("    membersonly: ").append(toIndentedString(membersonly)).append("\n");
    sb.append("    inviteNeedConfirm: ").append(toIndentedString(inviteNeedConfirm)).append("\n");
    sb.append("    custom: ").append(toIndentedString(custom)).append("\n");
    sb.append("    newowner: ").append(toIndentedString(newowner)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("groupname");
    openapiFields.add("avatar");
    openapiFields.add("description");
    openapiFields.add("public");
    openapiFields.add("maxusers");
    openapiFields.add("allowinvites");
    openapiFields.add("membersonly");
    openapiFields.add("invite_need_confirm");
    openapiFields.add("custom");
    openapiFields.add("newowner");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to EMModifyGroup
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
    if (jsonElement == null) {
      if (!EMModifyGroup.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
        throw new IllegalArgumentException(String.format("The required field(s) %s in EMModifyGroup is not found in the empty JSON string", EMModifyGroup.openapiRequiredFields.toString()));
      }
    }

    Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
    // check to see if the JSON string contains additional fields
    for (Map.Entry<String, JsonElement> entry : entries) {
      if (!EMModifyGroup.openapiFields.contains(entry.getKey())) {
        throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMModifyGroup` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
      }
    }
    JsonObject jsonObj = jsonElement.getAsJsonObject();
    if ((jsonObj.get("groupname") != null && !jsonObj.get("groupname").isJsonNull()) && !jsonObj.get("groupname").isJsonPrimitive()) {
      throw new IllegalArgumentException(String.format("Expected the field `groupname` to be a primitive type in the JSON string but got `%s`", jsonObj.get("groupname").toString()));
    }
    if ((jsonObj.get("avatar") != null && !jsonObj.get("avatar").isJsonNull()) && !jsonObj.get("avatar").isJsonPrimitive()) {
      throw new IllegalArgumentException(String.format("Expected the field `avatar` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar").toString()));
    }
    if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
      throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
    }
    if ((jsonObj.get("custom") != null && !jsonObj.get("custom").isJsonNull()) && !jsonObj.get("custom").isJsonPrimitive()) {
      throw new IllegalArgumentException(String.format("Expected the field `custom` to be a primitive type in the JSON string but got `%s`", jsonObj.get("custom").toString()));
    }
    if ((jsonObj.get("newowner") != null && !jsonObj.get("newowner").isJsonNull()) && !jsonObj.get("newowner").isJsonPrimitive()) {
      throw new IllegalArgumentException(String.format("Expected the field `newowner` to be a primitive type in the JSON string but got `%s`", jsonObj.get("newowner").toString()));
    }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
      if (!EMModifyGroup.class.isAssignableFrom(type.getRawType())) {
        return null; // this class only serializes 'EMModifyGroup' and its subtypes
      }
      final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
      final TypeAdapter<EMModifyGroup> thisAdapter
              = gson.getDelegateAdapter(this, TypeToken.get(EMModifyGroup.class));

      return (TypeAdapter<T>) new TypeAdapter<EMModifyGroup>() {
        @Override
        public void write(JsonWriter out, EMModifyGroup value) throws IOException {
          JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
          elementAdapter.write(out, obj);
        }

        @Override
        public EMModifyGroup read(JsonReader in) throws IOException {
          JsonElement jsonElement = elementAdapter.read(in);
          validateJsonElement(jsonElement);
          return thisAdapter.fromJsonTree(jsonElement);
        }

      }.nullSafe();
    }
  }

  /**
   * Create an instance of EMModifyGroup given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of EMModifyGroup
   * @throws IOException if the JSON string is invalid with respect to EMModifyGroup
   */
  public static EMModifyGroup fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMModifyGroup.class);
  }

  /**
   * Convert an instance of EMModifyGroup to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

