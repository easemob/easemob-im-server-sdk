package com.easemob.im.server.api.mute.detail;

import com.easemob.im.server.model.EMMute;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMuteDetailResponse {

    @JsonProperty("data")
    private MuteDetail muteDetail;

    @JsonCreator
    public GetMuteDetailResponse(@JsonProperty("data")
            MuteDetail muteDetail) {
        this.muteDetail = muteDetail;
    }

    public EMMute toEMMute() {
        return this.muteDetail.toEMMute();
    }

    private static class MuteDetail {

        @JsonProperty("userid")
        private String username;

        @JsonProperty("chat")
        private Integer chat;

        @JsonProperty("groupchat")
        private Integer groupChat;

        @JsonProperty("chatroom")
        private Integer chatroom;

        @JsonProperty("unixtime")
        private Integer unixTime;

        public EMMute toEMMute() {
            return new EMMute(this.username, this.chat, this.groupChat, this.chatroom, this.unixTime);
        }
    }
}
