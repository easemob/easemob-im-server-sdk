package com.easemob.im.server.api.notification;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.notification.settings.NotificationUserSetting;
import com.easemob.im.server.api.notification.settings.NotificationUserSettingUpdateRequest;
import com.easemob.im.server.model.EMNotificationUserSetting;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class NotificationApi {
    private Context context;

    public NotificationApi(Context context) {
        this.context = context;
    }

    /**
     * Update user's notification settings.
     *
     * @param username the user's username
     * @param requestCustomizer the request customizer
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> updateUserSetting(String username, Consumer<NotificationUserSettingUpdateRequest> requestCustomizer) {
        return NotificationUserSetting.update(this.context, username, requestCustomizer);
    }

    /**
     * Get user's notification settings.
     *
     * @param username the user's username
     * @return A {@code Mono} which emits {@code EMNotificationSettings}
     */
    public Mono<EMNotificationUserSetting> getUserSetting(String username) {
        return NotificationUserSetting.get(this.context, username);
    }
}
