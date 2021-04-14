package com.easemob.im.server.api.message.missed;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;

public class MessageMissed {
    private Context context;

    public MessageMissed(Context context) {
        this.context = context;
    }

    public Flux<MissedMessageCount> count(String username) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/offline_msg_count", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, MessageMissedCountResponse.class))
                        .flatMapIterable(MessageMissedCountResponse::getMissedMessageCounts));
    }
}
