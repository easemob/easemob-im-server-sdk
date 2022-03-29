package com.easemob.im.server.api.mute.mute;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MuteUserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("chat")
    private Integer chat;

    @JsonProperty("groupchat")
    private Integer groupChat;

    @JsonProperty("chatroom")
    private Integer chatroom;

    private MuteUserRequest() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String username;
        private Integer chat;
        private Integer groupChat;
        private Integer chatroom;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder chat(Integer chat) {
            this.chat = chat;
            return this;
        }

        public Builder groupChat(Integer groupChat) {
            this.groupChat = groupChat;
            return this;
        }

        public Builder chatroom(Integer chatroom) {
            this.chatroom = chatroom;
            return this;
        }

        public MuteUserRequest build() {
            MuteUserRequest muteUserRequest = new MuteUserRequest();
            muteUserRequest.username = this.username;
            muteUserRequest.chat = this.chat;
            muteUserRequest.groupChat = this.groupChat;
            muteUserRequest.chatroom = this.chatroom;
            return muteUserRequest;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "username=" + username +
                    ", chat=" + chat +
                    ", groupChat=" + groupChat +
                    ", chatroom=" + chatroom +
                    '}';
        }

    }
}
