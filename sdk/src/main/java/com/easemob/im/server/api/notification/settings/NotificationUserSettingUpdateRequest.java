package com.easemob.im.server.api.notification.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationUserSettingUpdateRequest {
    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("notification_display_style")
    private Integer showMessageContent;

    public NotificationUserSettingUpdateRequest() {
        this.nickname = null;
        this.showMessageContent = null;
    }

    @JsonCreator
    public NotificationUserSettingUpdateRequest(@JsonProperty("nickname") String nickname,
                                                @JsonProperty("notification_display_style") int showMessageContent) {
        this.nickname = nickname;
        this.showMessageContent = showMessageContent;
    }

    public NotificationUserSettingUpdateRequest withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public NotificationUserSettingUpdateRequest withShowMessageContent(boolean showMessageContent) {
        this.showMessageContent = showMessageContent ? 1 : 0;
        return this;
    }

    public boolean isEmpty() {
        return this.nickname == null && this.showMessageContent == null;
    }
}
