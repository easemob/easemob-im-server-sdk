package com.easemob.im.server.api.message.missed;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MessageMissed {
    private Context context;

    public MessageMissed(Context context) {
        this.context = context;
    }

    public Flux<MissedMessageCount> count(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/offline_msg_count", username))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, MessageMissedCountResponse.class))
                .flatMapIterable(MessageMissedCountResponse::getMissedMessageCounts);
    }
}
