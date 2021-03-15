package com.easemob.im.server.api.message.status;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMMessageStatus;
import reactor.core.publisher.Mono;

public class MessageStatus {

    public static Mono<EMMessageStatus> isMessageDeliveredToUser(Context context, String messageId, String username) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/offline_msg_status/%s", username, messageId))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, MessageStatusResponse.class))
            .flatMapIterable(MessageStatusResponse::getMessageStatuses)
            .map(ms -> ms.withToUsername(username))
            .next();
    }
}
