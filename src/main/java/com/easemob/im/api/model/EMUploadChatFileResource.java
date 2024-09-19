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
 * EMUploadChatFileResource
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-01-25T14:51:53.998371+08:00[Asia/Shanghai]")
public class EMUploadChatFileResource {
  public static final String SERIALIZED_NAME_UUID = "uuid";
  @SerializedName(SERIALIZED_NAME_UUID)
  private String uuid;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_SHARE_SECRET = "share-secret";
  @SerializedName(SERIALIZED_NAME_SHARE_SECRET)
  private String shareSecret;

  public EMUploadChatFileResource() {
  }

  public EMUploadChatFileResource uuid(String uuid) {
    
    this.uuid = uuid;
    return this;
  }

   /**
   * 文件 ID，即时通讯服务分配给该文件的唯一标识符。该参数在发送消息时需用到
   * @return uuid
  **/
  @javax.annotation.Nullable
  public String getUuid() {
    return uuid;
  }


  public void setUuid(String uuid) {
    this.uuid = uuid;
  }


  public EMUploadChatFileResource type(String type) {
    
    this.type = type;
    return this;
  }

   /**
   * 文件类型，为固定值 chatfile
   * @return type
  **/
  @javax.annotation.Nullable
  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public EMUploadChatFileResource shareSecret(String shareSecret) {
    
    this.shareSecret = shareSecret;
    return this;
  }

   /**
   * 文件访问密钥。你需要自行保存 share-secret，以便 下载文件时使用
   * @return shareSecret
  **/
  @javax.annotation.Nullable
  public String getShareSecret() {
    return shareSecret;
  }


  public void setShareSecret(String shareSecret) {
    this.shareSecret = shareSecret;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMUploadChatFileResource uploadChatFileResource = (EMUploadChatFileResource) o;
    return Objects.equals(this.uuid, uploadChatFileResource.uuid) &&
        Objects.equals(this.type, uploadChatFileResource.type) &&
        Objects.equals(this.shareSecret, uploadChatFileResource.shareSecret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, type, shareSecret);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMUploadChatFileResource {\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    shareSecret: ").append(toIndentedString(shareSecret)).append("\n");
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
    openapiFields.add("uuid");
    openapiFields.add("type");
    openapiFields.add("share-secret");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to EMUploadChatFileResource
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EMUploadChatFileResource.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in EMUploadChatFileResource is not found in the empty JSON string", EMUploadChatFileResource.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EMUploadChatFileResource.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMUploadChatFileResource` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("uuid") != null && !jsonObj.get("uuid").isJsonNull()) && !jsonObj.get("uuid").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `uuid` to be a primitive type in the JSON string but got `%s`", jsonObj.get("uuid").toString()));
      }
      if ((jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) && !jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if ((jsonObj.get("share-secret") != null && !jsonObj.get("share-secret").isJsonNull()) && !jsonObj.get("share-secret").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `share-secret` to be a primitive type in the JSON string but got `%s`", jsonObj.get("share-secret").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EMUploadChatFileResource.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EMUploadChatFileResource' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EMUploadChatFileResource> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EMUploadChatFileResource.class));

       return (TypeAdapter<T>) new TypeAdapter<EMUploadChatFileResource>() {
           @Override
           public void write(JsonWriter out, EMUploadChatFileResource value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EMUploadChatFileResource read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EMUploadChatFileResource given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EMUploadChatFileResource
  * @throws IOException if the JSON string is invalid with respect to EMUploadChatFileResource
  */
  public static EMUploadChatFileResource fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMUploadChatFileResource.class);
  }

 /**
  * Convert an instance of EMUploadChatFileResource to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
