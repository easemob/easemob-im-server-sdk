package com.easemob.im.server.api.notification.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationUserSetNicknameRequest {
    @JsonProperty("nickname")
    private String nickname;

    @JsonCreator
    public NotificationUserSetNicknameRequest(@JsonProperty("nickname") String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
