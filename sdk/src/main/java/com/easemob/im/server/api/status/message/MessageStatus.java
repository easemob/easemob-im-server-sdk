package com.easemob.im.server.api.status.message;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMMessageStatus;
import reactor.core.publisher.Mono;

public class MessageStatus {

    private Context context;

    private String messageId;

    public MessageStatus(Context context, String messageId) {
        this.context = context;
        this.messageId = messageId;
    }

    public Mono<EMMessageStatus> toUser(String username) {
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/offline_msg_status/%s", username, this.messageId))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, MessageStatusResponse.class))
            .flatMapIterable(MessageStatusResponse::getMessageStatuses)
            .map(ms -> ms.withToUsername(username))
            .next();
    }
}
