package com.easemob.im.server.api.message.modify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewMessage {

    @JsonProperty("type")
    private String type;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("customEvent")
    private String customEvent;

    @JsonProperty("customExts")
    private Map<String, String> customExts;

    public NewMessage() {
    }

    public static NewMessage.Builder builder() {
        return new NewMessage.Builder();
    }

    public static class Builder {
        private String type;

        private String msg;

        private String customEvent;

        private Map<String, String> customExts;

        public NewMessage.Builder type(String type) {
            this.type = type;
            return this;
        }

        public NewMessage.Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public NewMessage.Builder customEvent(String customEvent) {
            this.customEvent = customEvent;
            return this;
        }

        public NewMessage.Builder customExts(Map<String, String> customExts) {
            this.customExts = customExts;
            return this;
        }

        public NewMessage build() {
            NewMessage newMessage = new NewMessage();
            newMessage.type = this.type;
            newMessage.msg = this.msg;
            newMessage.customEvent = this.customEvent;
            newMessage.customExts = this.customExts;
            return newMessage;
        }

        @Override public String toString() {
            return "Builder{" +
                    "type='" + type + '\'' +
                    ", msg='" + msg + '\'' +
                    ", customEvent='" + customEvent + '\'' +
                    ", customExts=" + customExts +
                    '}';
        }
    }

}
