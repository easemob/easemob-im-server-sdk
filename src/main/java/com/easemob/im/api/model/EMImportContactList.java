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
 * EMImportContactList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-07-29T15:02:31.237631+08:00[Asia/Shanghai]")
public class EMImportContactList {
  public static final String SERIALIZED_NAME_USERNAMES = "usernames";
  @SerializedName(SERIALIZED_NAME_USERNAMES)
  private List<String> usernames;

  public EMImportContactList() {
  }

  public EMImportContactList usernames(List<String> usernames) {
    
    this.usernames = usernames;
    return this;
  }

  public EMImportContactList addUsernamesItem(String usernamesItem) {
    if (this.usernames == null) {
      this.usernames = new ArrayList<>();
    }
    this.usernames.add(usernamesItem);
    return this;
  }

   /**
   * 好友的用户 ID，一次最多可导入 10 个
   * @return usernames
  **/
  @javax.annotation.Nullable
  public List<String> getUsernames() {
    return usernames;
  }


  public void setUsernames(List<String> usernames) {
    this.usernames = usernames;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMImportContactList importContactList = (EMImportContactList) o;
    return Objects.equals(this.usernames, importContactList.usernames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usernames);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMImportContactList {\n");
    sb.append("    usernames: ").append(toIndentedString(usernames)).append("\n");
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
    openapiFields.add("usernames");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to EMImportContactList
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EMImportContactList.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in EMImportContactList is not found in the empty JSON string", EMImportContactList.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EMImportContactList.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMImportContactList` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the optional json data is an array if present
      if (jsonObj.get("usernames") != null && !jsonObj.get("usernames").isJsonNull() && !jsonObj.get("usernames").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `usernames` to be an array in the JSON string but got `%s`", jsonObj.get("usernames").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EMImportContactList.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EMImportContactList' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EMImportContactList> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EMImportContactList.class));

       return (TypeAdapter<T>) new TypeAdapter<EMImportContactList>() {
           @Override
           public void write(JsonWriter out, EMImportContactList value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EMImportContactList read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EMImportContactList given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EMImportContactList
  * @throws IOException if the JSON string is invalid with respect to EMImportContactList
  */
  public static EMImportContactList fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMImportContactList.class);
  }

 /**
  * Convert an instance of EMImportContactList to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
