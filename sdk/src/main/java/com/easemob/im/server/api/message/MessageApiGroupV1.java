package com.easemob.im.server.api.message;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.message.missed.MessageMissed;

public class MessageApiGroupV1 {

    private MessageMissed missed;

    public MessageApiGroupV1(Context context) {
        this.missed = new MessageMissed(context);
    }

    public MessageMissed missed() {
        return this.missed;
    }
}
