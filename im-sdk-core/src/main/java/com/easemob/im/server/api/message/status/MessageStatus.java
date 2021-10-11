package com.easemob.im.server.api.message.status;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class MessageStatus {

    private Context context;

    public MessageStatus(Context context) {
        this.context = context;
    }

    public Mono<Boolean> isMessageDeliveredToUser(String messageId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/offline_msg_status/%s", username, messageId))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, MessageStatusResponse.class))
                .map(rsp -> rsp.isMessageDelivered(messageId));
    }
}
