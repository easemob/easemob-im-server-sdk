package com.easemob.im.server.api.status;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.status.message.MessageStatus;
import com.easemob.im.server.api.status.online.UserStatus;

public class StatusApiV1 {

    private Context context;

    private UserStatus user;

    public StatusApiV1(Context context) {
        this.user = new UserStatus(context);
    }

    public UserStatus user() {
        return this.user;
    }

    public MessageStatus message(String messageId) {
        return new MessageStatus(this.context, messageId);
    }

}
