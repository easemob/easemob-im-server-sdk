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
 * EMSetPushPreferredLanguage
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-02-19T14:46:11.706022+08:00[Asia/Shanghai]")
public class EMSetPushPreferredLanguage {
  public static final String SERIALIZED_NAME_TRANSLATION_LANGUAGE = "translationLanguage";
  @SerializedName(SERIALIZED_NAME_TRANSLATION_LANGUAGE)
  private String translationLanguage;

  public EMSetPushPreferredLanguage() {
  }

  public EMSetPushPreferredLanguage translationLanguage(String translationLanguage) {
    
    this.translationLanguage = translationLanguage;
    return this;
  }

   /**
   * 用户接收的推送通知的首选语言的代码。如果设置为空字符串，表示无需翻译，服务器直接推送原语言的通知
   * @return translationLanguage
  **/
  @javax.annotation.Nullable
  public String getTranslationLanguage() {
    return translationLanguage;
  }


  public void setTranslationLanguage(String translationLanguage) {
    this.translationLanguage = translationLanguage;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMSetPushPreferredLanguage setPushPreferredLanguage = (EMSetPushPreferredLanguage) o;
    return Objects.equals(this.translationLanguage, setPushPreferredLanguage.translationLanguage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(translationLanguage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMSetPushPreferredLanguage {\n");
    sb.append("    translationLanguage: ").append(toIndentedString(translationLanguage)).append("\n");
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
    openapiFields.add("translationLanguage");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to EMSetPushPreferredLanguage
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EMSetPushPreferredLanguage.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in EMSetPushPreferredLanguage is not found in the empty JSON string", EMSetPushPreferredLanguage.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EMSetPushPreferredLanguage.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMSetPushPreferredLanguage` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("translationLanguage") != null && !jsonObj.get("translationLanguage").isJsonNull()) && !jsonObj.get("translationLanguage").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `translationLanguage` to be a primitive type in the JSON string but got `%s`", jsonObj.get("translationLanguage").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EMSetPushPreferredLanguage.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EMSetPushPreferredLanguage' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EMSetPushPreferredLanguage> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EMSetPushPreferredLanguage.class));

       return (TypeAdapter<T>) new TypeAdapter<EMSetPushPreferredLanguage>() {
           @Override
           public void write(JsonWriter out, EMSetPushPreferredLanguage value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EMSetPushPreferredLanguage read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EMSetPushPreferredLanguage given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EMSetPushPreferredLanguage
  * @throws IOException if the JSON string is invalid with respect to EMSetPushPreferredLanguage
  */
  public static EMSetPushPreferredLanguage fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMSetPushPreferredLanguage.class);
  }

 /**
  * Convert an instance of EMSetPushPreferredLanguage to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
