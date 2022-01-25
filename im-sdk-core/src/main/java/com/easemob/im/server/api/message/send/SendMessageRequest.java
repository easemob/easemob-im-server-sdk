package com.easemob.im.server.api.message.send;

import com.easemob.im.server.model.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class SendMessageRequest {
    @JsonProperty("from")
    private String from;

    @JsonProperty("target_type")
    private String targetType;

    /* the server side does not preserve order of targets */
    @JsonProperty("target")
    private Set<String> targets;

    @JsonProperty("msg")
    private Message message;

    @JsonProperty("ext")
    private Map<String, Object> extensions;

    @JsonProperty("routetype")
    private String routeType;

    @JsonCreator
    public SendMessageRequest(@JsonProperty("from") String from,
            @JsonProperty("target_type") String targetType,
            @JsonProperty("target") Set<String> targets,
            @JsonProperty("msg") EMMessage message,
            @JsonProperty("ext") Map<String, Object> extensions) {
        this.from = from;
        this.targetType = targetType;
        this.targets = targets;
        this.message = Message.of(message);
        this.extensions = extensions;
    }

    @JsonCreator
    public SendMessageRequest(@JsonProperty("from") String from,
                              @JsonProperty("target_type") String targetType,
                              @JsonProperty("target") Set<String> targets,
                              @JsonProperty("msg") EMMessage message,
                              @JsonProperty("ext") Map<String, Object> extensions,
                              @JsonProperty("routetype") String routeType) {
        this.from = from;
        this.targetType = targetType;
        this.targets = targets;
        this.message = Message.of(message);
        this.extensions = extensions;
        this.routeType = routeType;
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

    public String getTargetType() {
        return this.targetType;
    }

    public Set<String> getTargets() {
        return this.targets;
    }

    public Message getMessage() {
        return this.message;
    }

    public Set<EMKeyValue> getExtensions() {
        return populateExtensions(this.extensions);
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
        @JsonProperty("type")
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
        private Dimensions dimensions;

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
                @JsonProperty("size") Dimensions dimensions,
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
        public static Message of(EMMessage msg) {
            switch (msg.messageType()) {
                case TEXT:
                    return Message.of((EMTextMessage) msg);
                case IMAGE:
                    return Message.of((EMImageMessage) msg);
                case AUDIO:
                    return Message.of((EMVoiceMessage) msg);
                case VIDEO:
                    return Message.of((EMVideoMessage) msg);
                case LOCATION:
                    return Message.of((EMLocationMessage) msg);
                case FILE:
                    return Message.of((EMFileMessage) msg);
                case COMMAND:
                    return Message.of((EMCommandMessage) msg);
                case CUSTOM:
                    return Message.of((EMCustomMessage) msg);
                default:
                    throw new IllegalArgumentException(
                            String.format("message type %s not supported", msg.messageType()));
            }
        }

        public static Message of(EMTextMessage msg) {
            Message send = new Message("text");
            send.text = msg.text();
            return send;
        }

        public static Message of(EMImageMessage msg) {
            Message send = new Message("img");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            if (msg.width() != null || msg.height() != null) {
                send.dimensions = new Dimensions(msg.width(), msg.height());
            }
            return send;
        }

        public static Message of(EMVoiceMessage msg) {
            Message send = new Message("audio");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            send.duration = msg.duration();
            return send;
        }

        public static Message of(EMVideoMessage msg) {
            Message send = new Message("video");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            send.duration = msg.duration();
            send.thumb = msg.thumb();
            send.thumbSecret = msg.thumbSecret();
            return send;
        }

        public static Message of(EMLocationMessage msg) {
            Message send = new Message("loc");
            send.longitude = msg.longitude();
            send.latitude = msg.latitude();
            send.address = msg.address();
            return send;
        }

        public static Message of(EMFileMessage msg) {
            Message send = new Message("file");
            send.uri = msg.uri() == null ? null : msg.uri().toString();
            send.displayName = msg.displayName();
            send.bytes = msg.bytes();
            send.secret = msg.secret();
            return send;
        }

        public static Message of(EMCommandMessage msg) {
            Message send = new Message("cmd");
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

        public static Message of(EMCustomMessage msg) {
            Message send = new Message("custom");
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
