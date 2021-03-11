package com.easemob.im.server.api.message;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.message.missed.MessageMissed;
import com.easemob.im.server.api.message.status.MessageStatus;
import com.easemob.im.server.model.EMMessageStatus;
import reactor.core.publisher.Mono;

public class MessageApiGroupV1 {

    private Context context;

    private MessageMissed missed;

    public MessageApiGroupV1(Context context) {
        this.context = context;
        this.missed = new MessageMissed(context);
    }

    public MessageMissed missed() {
        return this.missed;
    }

    public Mono<EMMessageStatus> isMessageDeliveredToUser(String messageId, String toUser) {
        return MessageStatus.isMessageDeliveredToUser(this.context, messageId, toUser);
    }
}
