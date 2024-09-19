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
import com.easemob.im.api.model.EMMessageContent;
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
 * EMImportChatUserMessage
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-01-25T14:51:53.998371+08:00[Asia/Shanghai]")
public class EMImportChatUserMessage {
  public static final String SERIALIZED_NAME_FROM = "from";
  @SerializedName(SERIALIZED_NAME_FROM)
  private String from;

  public static final String SERIALIZED_NAME_TARGET = "target";
  @SerializedName(SERIALIZED_NAME_TARGET)
  private String target;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_BODY = "body";
  @SerializedName(SERIALIZED_NAME_BODY)
  private EMMessageContent body;

  public static final String SERIALIZED_NAME_IS_ACK_READ = "is_ack_read";
  @SerializedName(SERIALIZED_NAME_IS_ACK_READ)
  private Boolean isAckRead;

  public static final String SERIALIZED_NAME_MSG_TIMESTAMP = "msg_timestamp";
  @SerializedName(SERIALIZED_NAME_MSG_TIMESTAMP)
  private BigDecimal msgTimestamp;

  public static final String SERIALIZED_NAME_NEED_DOWNLOAD = "need_download";
  @SerializedName(SERIALIZED_NAME_NEED_DOWNLOAD)
  private Boolean needDownload;

  public EMImportChatUserMessage() {
  }

  public EMImportChatUserMessage from(String from) {
    
    this.from = from;
    return this;
  }

   /**
   * 消息发送方的用户 ID
   * @return from
  **/
  @javax.annotation.Nonnull
  public String getFrom() {
    return from;
  }


  public void setFrom(String from) {
    this.from = from;
  }


  public EMImportChatUserMessage target(String target) {
    
    this.target = target;
    return this;
  }

   /**
   * 消息接收方的用户 ID
   * @return target
  **/
  @javax.annotation.Nonnull
  public String getTarget() {
    return target;
  }


  public void setTarget(String target) {
    this.target = target;
  }


  public EMImportChatUserMessage type(String type) {
    
    this.type = type;
    return this;
  }

   /**
   * 消息类型： - txt：文本消息； - img：图片消息； - audio：语音消息； - video：视频消息； - file：文件消息； - loc：位置消息； - cmd：透传消息； - custom：自定义消息
   * @return type
  **/
  @javax.annotation.Nonnull
  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public EMImportChatUserMessage body(EMMessageContent body) {
    
    this.body = body;
    return this;
  }

   /**
   * Get body
   * @return body
  **/
  @javax.annotation.Nonnull
  public EMMessageContent getBody() {
    return body;
  }


  public void setBody(EMMessageContent body) {
    this.body = body;
  }


  public EMImportChatUserMessage isAckRead(Boolean isAckRead) {
    
    this.isAckRead = isAckRead;
    return this;
  }

   /**
   * 是否设置消息为已读。 - true：是； - false：否
   * @return isAckRead
  **/
  @javax.annotation.Nullable
  public Boolean getIsAckRead() {
    return isAckRead;
  }


  public void setIsAckRead(Boolean isAckRead) {
    this.isAckRead = isAckRead;
  }


  public EMImportChatUserMessage msgTimestamp(BigDecimal msgTimestamp) {
    
    this.msgTimestamp = msgTimestamp;
    return this;
  }

   /**
   * 要导入的消息的时间戳，单位为毫秒。若不传该参数，环信服务器会将导入的消息的时间戳设置为当前时间
   * @return msgTimestamp
  **/
  @javax.annotation.Nullable
  public BigDecimal getMsgTimestamp() {
    return msgTimestamp;
  }


  public void setMsgTimestamp(BigDecimal msgTimestamp) {
    this.msgTimestamp = msgTimestamp;
  }


  public EMImportChatUserMessage needDownload(Boolean needDownload) {
    
    this.needDownload = needDownload;
    return this;
  }

   /**
   * 是否需要下载附件并上传到服务器。 - true：是。这种情况下，需确保附件地址可直接访问，没有访问权限的限制。 - （默认）false：否
   * @return needDownload
  **/
  @javax.annotation.Nullable
  public Boolean getNeedDownload() {
    return needDownload;
  }


  public void setNeedDownload(Boolean needDownload) {
    this.needDownload = needDownload;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EMImportChatUserMessage importChatUserMessage = (EMImportChatUserMessage) o;
    return Objects.equals(this.from, importChatUserMessage.from) &&
        Objects.equals(this.target, importChatUserMessage.target) &&
        Objects.equals(this.type, importChatUserMessage.type) &&
        Objects.equals(this.body, importChatUserMessage.body) &&
        Objects.equals(this.isAckRead, importChatUserMessage.isAckRead) &&
        Objects.equals(this.msgTimestamp, importChatUserMessage.msgTimestamp) &&
        Objects.equals(this.needDownload, importChatUserMessage.needDownload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, target, type, body, isAckRead, msgTimestamp, needDownload);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EMImportChatUserMessage {\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    isAckRead: ").append(toIndentedString(isAckRead)).append("\n");
    sb.append("    msgTimestamp: ").append(toIndentedString(msgTimestamp)).append("\n");
    sb.append("    needDownload: ").append(toIndentedString(needDownload)).append("\n");
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
    openapiFields.add("from");
    openapiFields.add("target");
    openapiFields.add("type");
    openapiFields.add("body");
    openapiFields.add("is_ack_read");
    openapiFields.add("msg_timestamp");
    openapiFields.add("need_download");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("from");
    openapiRequiredFields.add("target");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("body");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to EMImportChatUserMessage
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EMImportChatUserMessage.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in EMImportChatUserMessage is not found in the empty JSON string", EMImportChatUserMessage.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EMImportChatUserMessage.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `EMImportChatUserMessage` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : EMImportChatUserMessage.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("from").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `from` to be a primitive type in the JSON string but got `%s`", jsonObj.get("from").toString()));
      }
      if (!jsonObj.get("target").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `target` to be a primitive type in the JSON string but got `%s`", jsonObj.get("target").toString()));
      }
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      // validate the required field `body`
      EMMessageContent.validateJsonElement(jsonObj.get("body"));
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EMImportChatUserMessage.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EMImportChatUserMessage' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EMImportChatUserMessage> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EMImportChatUserMessage.class));

       return (TypeAdapter<T>) new TypeAdapter<EMImportChatUserMessage>() {
           @Override
           public void write(JsonWriter out, EMImportChatUserMessage value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EMImportChatUserMessage read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EMImportChatUserMessage given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EMImportChatUserMessage
  * @throws IOException if the JSON string is invalid with respect to EMImportChatUserMessage
  */
  public static EMImportChatUserMessage fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EMImportChatUserMessage.class);
  }

 /**
  * Convert an instance of EMImportChatUserMessage to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
