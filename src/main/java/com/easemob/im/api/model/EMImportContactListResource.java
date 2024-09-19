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

import com.easemob.im.JSON;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.*;

/**
 * EMImportContactListResource
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-07-29T15:02:31.237631+08:00[Asia/Shanghai]")
public class EMImportContactListResource {
  public static final String SERIALIZED_NAME_UN_KNOW_FAILED = "UnKnowFailed";
  @SerializedName(SERIALIZED_NAME_UN_KNOW_FAILED)
  private List<String> unKnowFailed;

  public static final String SERIALIZED_NAME_SUCCESS = "success";
  @SerializedName(SERIALIZED_NAME_SUCCESS)
  private List<String> success;

  public static final String SERIALIZED_NAME_NOT_EXIST_FAILED = "NotExistFailed";
  @SerializedName(SERIALIZED_NAME_NOT_EXIST_FAILED)
  private List<String> notExistFailed;

  public static final String SERIALIZED_NAME_MAX_LIMIT_FAILED = "maxLimitFailed";
  @SerializedName(SERIALIZED_NAME_MAX_LIMIT_FAILED)
  private List<String> maxLimitFailed;

  public EMImportContactListResource() {
  }

  public EMImportContactListResource unKnowFailed(List<String> unKnowFailed) {
    
    this.unKnowFailed = unKnowFailed;
    return this;
  }

  public EMImportContactListResource addUnKnowFailedItem(String unKnowFailedItem) {
    if (this.unKnowFailed == null) {
      this.unKnowFailed = new ArrayList<>();
    }
    this.unKnowFailed.add(unKnowFailedItem);
    return this;
  }

   /**
   * 因系统异常添加失败的好友的用户 ID
   * @return unKnowFailed
  **/
  @javax.annotation.Nullable
  public List<String> getUnKnowFailed() {
    return unKnowFailed;
  }


  public void setUnKnowFailed(List<String> unKnowFailed) {
    this.unKnowFailed = unKnowFailed;
  }


  public EMImportContactListResource success(List<String> success) {
    
    this.success = success;
    return this;
  }

  public EMImportContactListResource addSuccessItem(String successItem) {
    if (this.success == null) {
      this.success = new ArrayList<>();
    }
    this.success.add(successItem);
    return this;
  }

   /**
   * 成功添加好友的用户 ID
   * @return success
  **/
  @javax.annotation.Nullable
  public List<String> getSuccess() {
    return success;
  }


  public void setSuccess(List<String> success) {
    this.success = success;
  }


  public EMImportContactListResource notExistFailed(List<String> notExistFailed) {
    
    this.notExistFailed = notExistFailed;
    return this;
  }

  public EMImportContactListResource addNotExistFailedItem(String notExistFailedItem) {
    if (this.notExistFailed == null) {
      this.notExistFailed = new ArrayList<>();
    }
    this.notExistFailed.add(notExistFailedItem);
    return this;
  }

   /**
   * 不存在的好友的用户 ID
   * @return notExistFailed
  **/
  @javax.annotation.Nullable
  public List<String> getNotExistFailed() {
    return notExistFailed;
  }


  public void setNotExistFailed(List<String> notExistFailed) {
    this.notExistFailed = notExistFailed;
  }


  public EMImportContactListResource maxLimitFailed(List<String> maxLimitFailed) {
    
    this.maxLimitFailed = maxLimitFailed;
    return this;
  }

  public EMImportContactListResource addMaxLimitFailedItem(String maxLimitFailedItem) {
    if (this.maxLimitFailed == null) {
      this.maxLimitFailed = new ArrayList<>();
    }
    this.maxLimitFailed.add(maxLimitFailedItem);
    return this;
  }

   /**
   * 因导入的好友已达上限而导入失败的好友的用户 ID
   * @return maxLimitFailed
  **/
  @javax.annotation.Nullable
  public List<String> getMaxLimitFailed() {
    return maxLimitFailed;
  }


  public void setMaxLimitFailed(List<String> maxLimitFailed) {
    this.maxLimitFailed = maxLimitFailed;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMImportContactListResource importContactListResource = (EMImportContactListResource) o;
    return Objects.equals(this.unKnowFailed, importContactListResource.unKnowFailed) &&
        Objects.equals(this.success, importContactListResource.success) &&
        Objects.equals(this.notExistFailed, importContactListResource.notExistFailed) &&
        Objects.equals(this.maxLimitFailed, importContactListResource.maxLimitFailed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unKnowFailed, success, notExistFailed, maxLimitFailed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMImportContactListResource {\n");
    sb.append("    unKnowFailed: ").append(toIndentedString(unKnowFailed)).append("\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    notExistFailed: ").append(toIndentedString(notExistFailed)).append("\n");
    sb.append("    maxLimitFailed: ").append(toIndentedString(maxLimitFailed)).append("\n");
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
    openapiFields.add("UnKnowFailed");
    openapiFields.add("success");
    openapiFields.add("NotExistFailed");
    openapiFields.add("maxLimitFailed");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to EMImportContactListResource
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EMImportContactListResource.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in EMImportContactListResource is not found in the empty JSON string", EMImportContactListResource.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EMImportContactListResource.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMImportContactListResource` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the optional json data is an array if present
      if (jsonObj.get("UnKnowFailed") != null && !jsonObj.get("UnKnowFailed").isJsonNull() && !jsonObj.get("UnKnowFailed").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `UnKnowFailed` to be an array in the JSON string but got `%s`", jsonObj.get("UnKnowFailed").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("success") != null && !jsonObj.get("success").isJsonNull() && !jsonObj.get("success").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `success` to be an array in the JSON string but got `%s`", jsonObj.get("success").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("NotExistFailed") != null && !jsonObj.get("NotExistFailed").isJsonNull() && !jsonObj.get("NotExistFailed").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `NotExistFailed` to be an array in the JSON string but got `%s`", jsonObj.get("NotExistFailed").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("maxLimitFailed") != null && !jsonObj.get("maxLimitFailed").isJsonNull() && !jsonObj.get("maxLimitFailed").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `maxLimitFailed` to be an array in the JSON string but got `%s`", jsonObj.get("maxLimitFailed").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EMImportContactListResource.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EMImportContactListResource' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EMImportContactListResource> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EMImportContactListResource.class));

       return (TypeAdapter<T>) new TypeAdapter<EMImportContactListResource>() {
           @Override
           public void write(JsonWriter out, EMImportContactListResource value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EMImportContactListResource read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EMImportContactListResource given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EMImportContactListResource
  * @throws IOException if the JSON string is invalid with respect to EMImportContactListResource
  */
  public static EMImportContactListResource fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMImportContactListResource.class);
  }

 /**
  * Convert an instance of EMImportContactListResource to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
