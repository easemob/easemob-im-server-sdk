package com.easemob.im.server.api.mute.list;

import com.easemob.im.server.model.EMMute;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetMuteListResponse {

    @JsonProperty("data")
    private MuteData muteData;

    @JsonCreator
    public GetMuteListResponse(@JsonProperty("data") MuteData muteData) {
        this.muteData = muteData;
    }

    public List<EMMute> toEMMute() {
        if (this.muteData != null && this.muteData.detailList != null && this.muteData.detailList.size() > 0) {
            Integer uniTime = muteData.unixTime;
            return this.muteData.detailList.stream().map(detail -> {
                        detail.unixTime = uniTime;
                        return detail.toEMMute();
                    })
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    private static class MuteData {
        @JsonProperty("data")
        private List<MuteListDetail> detailList;

        @JsonProperty("unixtime")
        private Integer unixTime;
    }


    private static class MuteListDetail {
        @JsonProperty("username")
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
            return new EMMute(this.username, this.chat, this.groupChat, this.chatroom,
                    this.unixTime);
        }
    }
}
