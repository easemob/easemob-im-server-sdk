package com.easemob.im.server.api.notification;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.notification.nodisturbing.get.GetNoDisturbing;
import com.easemob.im.server.model.EMNotificationUserSetting;
import reactor.core.publisher.Mono;

/**
 * 推送通知免打扰API
 */
public class NotificationApi {
    private GetNoDisturbing noDisturbing;

    public NotificationApi(Context context) {
        this.noDisturbing = new GetNoDisturbing(context);
    }

    /**
     * 获取推送通知免打扰信息
     * @param username  用户名
     * @return 返回免打扰响应或错误
     */
    public Mono<EMNotificationUserSetting> getNoDisturbing(String username) {
        return this.noDisturbing.getNoDisturbing(username);
    }
}
