package com.easemob.im.cli.model;

import com.easemob.im.server.api.message.MessageApi;
import com.easemob.im.server.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import static com.easemob.im.server.model.EMMessage.MessageType.*;

public class MessageFile {

    @JsonProperty(value = "from", required = true)
    private String from;

    @JsonProperty(value = "toType", required = true)
    private EMEntity.EntityType toType;

    @JsonProperty(value = "toIds", required = true)
    private Set<String> toIds;

    @JsonProperty(value = "body", required = true)
    private MessageBody body;

    @JsonProperty("exts")
    private Map<String, Object> extensions;

    public Mono<EMSentMessageIds> send(MessageApi messageApi) {
        return messageApi.send(from, this.toTypeStr(), toIds, body.toEMMessage(), EMKeyValue.of(extensions));
    }

    private String toTypeStr() {
        switch (this.toType) {
            case USER:
                return "users";
            case GROUP:
                return "groups";
            case ROOM:
                return "rooms";
            default:
                return null;
        }
    }

    public static class MessageBody {

        @JsonProperty("type")
        private EMMessage.MessageType type;

        @JsonProperty("text")
        private String text;

        @JsonProperty("filename")
        private String filename;

        /**
         * 附件URL
         * 格式为：环信域名/orgname/appname/chatfiles/成功上传文件返回的UUID
         * 例：https://a1.easemob.com/easemob-demo/testapp/chatfiles/55f12940-64af-11e4-8a5b-ff2336f03252
         */
        @JsonProperty("url")
        private URI url;

        /**
         * 附件密钥
         * 若附件是通过环信图床上传，则需要携带
         */
        @JsonProperty("secret")
        private String secret;

        @JsonProperty("lng")
        private Double longitude;

        @JsonProperty("lat")
        private Double latitude;

        @JsonProperty("addr")
        private String address;

        /**
         * 字节长度
         * 单位：字节
         */
        @JsonProperty(value = "bytes", defaultValue = "0")
        private Integer bytes;

        @JsonProperty("action")
        private String action;

        @JsonProperty("param")
        private Map<String, Object> params;

        /**
         * 视频/音频时长
         * 单位：秒
         */
        @JsonProperty("duration")
        private Integer duration;

        /**
         * 图片大小
         * 单位：像素
         */
        @JsonProperty("size")
        private Size size;

        @JsonProperty("customEvent")
        private String customEvent;

        @JsonProperty("customExts")
        private Map<String, Object> customExtensions;

        private EMMessage toEMMessage() {
            if (this.type == TEXT) {
                return new EMTextMessage().text(this.text);
            } else if (this.type == IMAGE) {
                return new EMImageMessage().displayName(filename).uri(url).secret(secret).bytes(bytes);
            } else if (this.type == AUDIO) {
                return new EMVoiceMessage().displayName(filename).uri(url).secret(secret).duration(duration).bytes(bytes);
            } else if (this.type == VIDEO) {
                return new EMVideoMessage().displayName(filename).uri(url).secret(secret).duration(duration).bytes(bytes);
            } else if (this.type == LOCATION) {
                return new EMLocationMessage().address(address).latitude(latitude).longitude(longitude);
            } else if (this.type == FILE) {
                return new EMFileMessage().displayName(filename).uri(url).secret(secret).bytes(bytes);
            } else if (this.type == COMMAND) {
                return new EMCommandMessage().action(action).params(EMKeyValue.of(params));
            } else if (this.type == CUSTOM) {
                return new EMCustomMessage().customEvent(customEvent).customExtensions(EMKeyValue.of(customExtensions));
            } else {
                return null;
            }
        }

        public EMMessage.MessageType getType() {
            return type;
        }

        public void setType(EMMessage.MessageType type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public URI getUrl() {
            return url;
        }

        public void setUri(URI url) {
            this.url = url;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getBytes() {
            return bytes;
        }

        public void setBytes(Integer bytes) {
            this.bytes = bytes;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Size getSize() {
            return size;
        }

        public void setSize(Size size) {
            this.size = size;
        }

        public String getCustomEvent() {
            return customEvent;
        }

        public void setCustomEvent(String customEvent) {
            this.customEvent = customEvent;
        }

        public Map<String, Object> getCustomExtensions() {
            return customExtensions;
        }

        public void setCustomExtensions(Map<String, Object> customExtensions) {
            this.customExtensions = customExtensions;
        }
    }

    private static class Size {

        @JsonProperty("width")
        private int width;

        @JsonProperty("height")
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public EMEntity.EntityType getToType() {
        return toType;
    }

    public void setToType(EMEntity.EntityType toType) {
        this.toType = toType;
    }

    public Set<String> getToIds() {
        return toIds;
    }

    public void setToIds(Set<String> toIds) {
        this.toIds = toIds;
    }

    public MessageBody getBody() {
        return body;
    }

    public void setBody(MessageBody body) {
        this.body = body;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }
}
