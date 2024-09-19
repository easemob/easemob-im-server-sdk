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
import java.math.BigDecimal;
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
 * EMGetMultipleUsersPresenceStatusResource
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-02-19T14:46:11.706022+08:00[Asia/Shanghai]")
public class EMGetMultipleUsersPresenceStatusResource {
  public static final String SERIALIZED_NAME_UID = "uid";
  @SerializedName(SERIALIZED_NAME_UID)
  private String uid;

  public static final String SERIALIZED_NAME_LAST_TIME = "last_time";
  @SerializedName(SERIALIZED_NAME_LAST_TIME)
  private BigDecimal lastTime;

  public static final String SERIALIZED_NAME_EXT = "ext";
  @SerializedName(SERIALIZED_NAME_EXT)
  private String ext;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private Object status;

  public EMGetMultipleUsersPresenceStatusResource() {
  }

  public EMGetMultipleUsersPresenceStatusResource uid(String uid) {
    
    this.uid = uid;
    return this;
  }

   /**
   * 用户在即时通讯服务器的唯一 ID
   * @return uid
  **/
  @javax.annotation.Nullable
  public String getUid() {
    return uid;
  }


  public void setUid(String uid) {
    this.uid = uid;
  }


  public EMGetMultipleUsersPresenceStatusResource lastTime(BigDecimal lastTime) {
    
    this.lastTime = lastTime;
    return this;
  }

   /**
   * 用户的最近在线时间，Unix 时间戳，单位为秒
   * @return lastTime
  **/
  @javax.annotation.Nullable
  public BigDecimal getLastTime() {
    return lastTime;
  }


  public void setLastTime(BigDecimal lastTime) {
    this.lastTime = lastTime;
  }


  public EMGetMultipleUsersPresenceStatusResource ext(String ext) {
    
    this.ext = ext;
    return this;
  }

   /**
   * 用户的在线状态扩展信息
   * @return ext
  **/
  @javax.annotation.Nullable
  public String getExt() {
    return ext;
  }


  public void setExt(String ext) {
    this.ext = ext;
  }


  public EMGetMultipleUsersPresenceStatusResource status(Object status) {
    
    this.status = status;
    return this;
  }

   /**
   * 用户在多个设备上的在线状态： - 0： 离线。 - 1： 在线。 - 其他值：用户自定义的在线状态
   * @return status
  **/
  @javax.annotation.Nullable
  public Object getStatus() {
    return status;
  }


  public void setStatus(Object status) {
    this.status = status;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMGetMultipleUsersPresenceStatusResource getMultipleUsersPresenceStatusResource = (EMGetMultipleUsersPresenceStatusResource) o;
    return Objects.equals(this.uid, getMultipleUsersPresenceStatusResource.uid) &&
        Objects.equals(this.lastTime, getMultipleUsersPresenceStatusResource.lastTime) &&
        Objects.equals(this.ext, getMultipleUsersPresenceStatusResource.ext) &&
        Objects.equals(this.status, getMultipleUsersPresenceStatusResource.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid, lastTime, ext, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMGetMultipleUsersPresenceStatusResource {\n");
    sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
    sb.append("    lastTime: ").append(toIndentedString(lastTime)).append("\n");
    sb.append("    ext: ").append(toIndentedString(ext)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
    openapiFields.add("uid");
    openapiFields.add("last_time");
    openapiFields.add("ext");
    openapiFields.add("status");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to EMGetMultipleUsersPresenceStatusResource
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EMGetMultipleUsersPresenceStatusResource.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in EMGetMultipleUsersPresenceStatusResource is not found in the empty JSON string", EMGetMultipleUsersPresenceStatusResource.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EMGetMultipleUsersPresenceStatusResource.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMGetMultipleUsersPresenceStatusResource` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("uid") != null && !jsonObj.get("uid").isJsonNull()) && !jsonObj.get("uid").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `uid` to be a primitive type in the JSON string but got `%s`", jsonObj.get("uid").toString()));
      }
      if ((jsonObj.get("ext") != null && !jsonObj.get("ext").isJsonNull()) && !jsonObj.get("ext").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ext` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ext").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EMGetMultipleUsersPresenceStatusResource.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EMGetMultipleUsersPresenceStatusResource' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EMGetMultipleUsersPresenceStatusResource> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EMGetMultipleUsersPresenceStatusResource.class));

       return (TypeAdapter<T>) new TypeAdapter<EMGetMultipleUsersPresenceStatusResource>() {
           @Override
           public void write(JsonWriter out, EMGetMultipleUsersPresenceStatusResource value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EMGetMultipleUsersPresenceStatusResource read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EMGetMultipleUsersPresenceStatusResource given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EMGetMultipleUsersPresenceStatusResource
  * @throws IOException if the JSON string is invalid with respect to EMGetMultipleUsersPresenceStatusResource
  */
  public static EMGetMultipleUsersPresenceStatusResource fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMGetMultipleUsersPresenceStatusResource.class);
  }

 /**
  * Convert an instance of EMGetMultipleUsersPresenceStatusResource to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
