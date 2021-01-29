package com.easemob.im.server.api.notification;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.notification.user.NotificationUser;

public class NotificationV1 {

    private NotificationUser notificationUser;

    public NotificationV1(Context context) {
        this.notificationUser = new NotificationUser(context);
    }

    public NotificationUser user() {
        return this.notificationUser;
    }
}
