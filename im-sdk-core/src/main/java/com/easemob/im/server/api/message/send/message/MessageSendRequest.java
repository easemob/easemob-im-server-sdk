package com.easemob.im.server.api.message.send.message;

import com.easemob.im.server.model.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class MessageSendRequest {

    @JsonProperty("from")
    private String from;

    @JsonProperty("type")
    private String msgType;

    /* the server side does not preserve order of targets */
    @JsonProperty("to")
    private Set<String> tos;

    @JsonProperty("body")
    private MessageSendRequest.Message body;

    @JsonProperty("ext")
    private Map<String, Object> extensions;

    @JsonProperty("routetype")
    private String routeType;

    @JsonProperty("sync_device")
    private Boolean syncDevice;

    @JsonCreator
    public MessageSendRequest(@JsonProperty("from") String from,
            @JsonProperty("to") Set<String> toSet,
            @JsonProperty("msg") EMMessage message,
            @JsonProperty("ext") Map<String, Object> extensions) {
        this.from = from;
        this.tos = toSet;
        Message send = Message.of(message);
        this.msgType = send.type;
        this.body = send;
        this.extensions = extensions;
    }

    @JsonCreator
    public MessageSendRequest(@JsonProperty("from") String from,
            @JsonProperty("to") Set<String> toSet,
            @JsonProperty("msg") EMMessage message,
            @JsonProperty("ext") Map<String, Object> extensions,
            @JsonProperty("routetype") String routeType) {
        this.from = from;
        this.tos = toSet;
        Message send = Message.of(message);
        this.msgType = send.type;
        this.body = send;
        this.extensions = extensions;
        this.routeType = routeType;
    }

    @JsonCreator
    public MessageSendRequest(@JsonProperty("from") String from,
            @JsonProperty("to") Set<String> toSet,
            @JsonProperty("msg") EMMessage message,
            @JsonProperty("ext") Map<String, Object> extensions,
            @JsonProperty("sync_device") Boolean syncDevice) {
        this.from = from;
        this.tos = toSet;
        Message send = Message.of(message);
        this.msgType = send.type;
        this.body = send;
        this.extensions = extensions;
        this.syncDevice = syncDevice;
    }

    @JsonCreator
    public MessageSendRequest(@JsonProperty("from") String from,
            @JsonProperty("to") Set<String> toSet,
            @JsonProperty("msg") EMMessage message,
            @JsonProperty("ext") Map<String, Object> extensions,
            @JsonProperty("routetype") String routeType,
            @JsonProperty("sync_device") Boolean syncDevice) {
        this.from = from;
        this.tos = toSet;
        Message send = Message.of(message);
        this.msgType = send.type;
        this.body = send;
        this.extensions = extensions;
        this.routeType = routeType;
        this.syncDevice = syncDevice;
    }

    public static Map<String, Object> parseExtensions(Set<EMKeyValue> extensions) {
        if (extensions == null || extensions.isEmpty()) {
            return null;
        }
        Map<String, Object> mapped = new HashMap<>();
        for (EMKeyValue keyValue : extensions) {
            switch (keyValue.type()) {
                case BOOL:
                    mapped.put(keyValue.key(), keyValue.asBoolean());
                    break;
                case INT:
                    mapped.put(keyValue.key(), keyValue.asInt());
                    break;
                case UINT:
                    mapped.put(keyValue.key(), keyValue.asLong());
                    break;
                case LLINT:
                    mapped.put(keyValue.key(), keyValue.asLong());
                    break;
                case FLOAT:
                    mapped.put(keyValue.key(), keyValue.asFloat());
                    break;
                case DOUBLE:
                    mapped.put(keyValue.key(), keyValue.asDouble());
                    break;
                case STRING:
                    mapped.put(keyValue.key(), keyValue.asString());
                    break;
                case JSON_STRING:
                    mapped.put(keyValue.key(), keyValue.asString());
                    break;
                case OBJECT:
                    mapped.put(keyValue.key(), keyValue.asObject());
                    break;
            }
        }
        return mapped;
    }

    public String getFrom() {
        return this.from;
    }

    public Set<String> getTos() {
        return this.tos;
    }

    public String getMsgType(){
        return this.msgType;
    }

    public Message getBody() {
        return this.body;
    }

    public Set<EMKeyValue> getExtensions() {
        return populateExtensions(this.extensions);
    }

    public String getRouteType() {
        return routeType;
    }

    public Boolean getSyncDevice() {
        return syncDevice;
    }

    @SuppressWarnings("unchecked")
    public Set<EMKeyValue> populateExtensions(Map<String, Object> extensions) {
        if (extensions == null || extensions.isEmpty()) {
            return null;
        }
        Set<EMKeyValue> mapped = new HashSet<>();
        for (String key : extensions.keySet()) {
            Object value = extensions.get(key);
            if (value instanceof Boolean) {
                mapped.add(EMKeyValue.of(key, (boolean) value));
            } else if (value instanceof Integer) {
                mapped.add(EMKeyValue.of(key, (int) value));
            } else if (value instanceof Long) {
                mapped.add(EMKeyValue.of(key, (long) value));
            } else if (value instanceof Float) {
                mapped.add(EMKeyValue.of(key, (float) value));
            } else if (value instanceof Double) {
                mapped.add(EMKeyValue.of(key, (double) value));
            } else if (value instanceof String) {
                mapped.add(EMKeyValue.of(key, (String) value));
            }
        }
        return mapped;
    }

    static class Message {

        @JsonIgnore
        private String type;

        @JsonProperty("msg")
        private String text;

        @JsonProperty("lng")
        private Double longitude;

        @JsonProperty("lat")
        private Double latitude;

        @JsonProperty("addr")
        private String address;

        @JsonProperty("filename")
        private String displayName;

        @JsonProperty("url")
        private String uri;

        @JsonProperty("secret")
        private String secret;

        @JsonProperty("file_length")
        private Integer bytes;

        @JsonProperty("action")
        private String action;

        @JsonProperty("param")
        private Map<String, Object> params;

        @JsonProperty("length")
        private Integer duration;

        @JsonProperty("size")
        private MessageSendRequest.Dimensions dimensions;

        @JsonProperty("thumb")
        private String thumb;

        @JsonProperty("thumb_secret")
        private String thumbSecret;

        @JsonProperty("customEvent")
        private String customEvent;

        @JsonProperty("customExts")
        private Map<String, Object> customExtensions;

        @JsonCreator
        public Message(@JsonProperty("type") String type,
                @JsonProperty("msg") String text,
                @JsonProperty("lng") Double longitude,
                @JsonProperty("lat") Double latitude,
                @JsonProperty("addr") String address,
                @JsonProperty("filename") String displayName,
                @JsonProperty("url") String uri,
                @JsonProperty("secret") String secretKey,
                @JsonProperty("file_length") Integer bytes,
                @JsonProperty("action") String action,
                @JsonProperty("param") Map<String, Object> params,
                @JsonProperty("length") Integer duration,
                @JsonProperty("size") MessageSendRequest.Dimensions dimensions,
                @JsonProperty("customEvent") String customEvent,
                @JsonProperty("customExts") Map<String, Object> customExtensions) {
            this.type = type;
            this.text = text;
            this.longitude = longitude;
            this.latitude = latitude;
            this.address = address;
            this.displayName = displayName;
            this.uri = uri;
            this.secret = secretKey;
            this.bytes = bytes;
            this.action = action;
            this.params = params;
            this.duration = duration;
            this.dimensions = dimensions;
            this.customEvent = customEvent;
            this.customExtensions = customExtensions;
        }

        public Message(String type) {
            this.type = type;
        }

        @SuppressWarnings("unchecked")
        public static MessageSendRequest.Message of(EMMessage msg) {
            switch (msg.messageType()) {
                case TEXT:
                    return MessageSendRequest.Message.of((EMTextMessage) msg);
                case IMAGE:
                    return MessageSendRequest.Message.of((EMImageMessage) msg);
                case AUDIO:
                    return MessageSendRequest.Message.of((EMVoiceMessage) msg);
                case VIDEO:
                    return MessageSendRequest.Message.of((EMVideoMessage) msg);
                case LOCATION:
                    return MessageSendRequest.Message.of((EMLocationMessage) msg);
                case FILE:
                    return MessageSendRequest.Message.of((EMFileMessage) msg);
                case COMMAND:
                    return MessageSendRequest.Message.of((EMCommandMessage) msg);
                case CUSTOM:
                    return MessageSendRequest.Message.of((EMCustomMessage) msg);
                default:
                    throw new IllegalArgumentException(
                            String.format("message type %s not supported", msg.messageType()));
            }
        }

        public static MessageSendRequest.Message of(EMTextMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("txt");
            send.text = msg.text();
            return send;
        }

        public static MessageSendRequest.Message of(EMImageMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("img");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            if (msg.width() != null || msg.height() != null) {
                send.dimensions = new MessageSendRequest.Dimensions(msg.width(), msg.height());
            }
            return send;
        }

        public static MessageSendRequest.Message of(EMVoiceMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("audio");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            send.duration = msg.duration();
            return send;
        }

        public static MessageSendRequest.Message of(EMVideoMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("video");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            send.duration = msg.duration();
            send.thumb = msg.thumb();
            send.thumbSecret = msg.thumbSecret();
            return send;
        }

        public static MessageSendRequest.Message of(EMLocationMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("loc");
            send.longitude = msg.longitude();
            send.latitude = msg.latitude();
            send.address = msg.address();
            return send;
        }

        public static MessageSendRequest.Message of(EMFileMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("file");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            return send;
        }

        public static MessageSendRequest.Message of(EMCommandMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("cmd");
            send.action = msg.action();
            if (msg.params() == null) {
                return send;
            }
            send.params = new HashMap<>();
            for (EMKeyValue kv : msg.params()) {
                switch (kv.type()) {
                    case BOOL:
                        send.params.put(kv.key(), kv.asBoolean());
                        break;
                    case INT:
                        send.params.put(kv.key(), kv.asInt());
                        break;
                    case UINT:
                        send.params.put(kv.key(), kv.asLong());
                        break;
                    case LLINT:
                        send.params.put(kv.key(), kv.asLong());
                        break;
                    case FLOAT:
                        send.params.put(kv.key(), kv.asFloat());
                        break;
                    case DOUBLE:
                        send.params.put(kv.key(), kv.asDouble());
                        break;
                    case STRING:
                        send.params.put(kv.key(), kv.asString());
                        break;
                    case JSON_STRING:
                        send.params.put(kv.key(), kv.asString());
                        break;
                }
            }
            return send;
        }

        public static MessageSendRequest.Message of(EMCustomMessage msg) {
            MessageSendRequest.Message send = new MessageSendRequest.Message("custom");
            send.customEvent = msg.customEvent();
            if (msg.customExtensions() == null) {
                return send;
            }
            send.customExtensions = new HashMap<>();
            for (EMKeyValue kv : msg.customExtensions()) {
                switch (kv.type()) {
                    case BOOL:
                        send.customExtensions.put(kv.key(), kv.asBoolean());
                        break;
                    case INT:
                        send.customExtensions.put(kv.key(), kv.asInt());
                        break;
                    case UINT:
                        send.customExtensions.put(kv.key(), kv.asLong());
                        break;
                    case LLINT:
                        send.customExtensions.put(kv.key(), kv.asLong());
                        break;
                    case FLOAT:
                        send.customExtensions.put(kv.key(), kv.asFloat());
                        break;
                    case DOUBLE:
                        send.customExtensions.put(kv.key(), kv.asDouble());
                        break;
                    case STRING:
                        send.customExtensions.put(kv.key(), kv.asString());
                        break;
                    case JSON_STRING:
                        send.customExtensions.put(kv.key(), kv.asString());
                        break;
                }
            }
            return send;
        }
    }


    static class Dimensions {
        @JsonProperty("width")
        private Double width;
        @JsonProperty("height")
        private Double height;

        @JsonCreator
        public Dimensions(@JsonProperty("width") Double width,
                @JsonProperty("height") Double height) {
            this.width = width;
            this.height = height;
        }

        public Double getWidth() {
            return this.width;
        }

        public Double getHeight() {
            return this.height;
        }
    }

}
