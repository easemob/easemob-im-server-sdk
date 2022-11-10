package com.easemob.im.server.api.message.status;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, MessageStatusResponse.class))
                .map(rsp -> rsp.isMessageDelivered(messageId));
    }
}
