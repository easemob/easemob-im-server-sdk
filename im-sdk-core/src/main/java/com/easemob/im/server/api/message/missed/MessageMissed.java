package com.easemob.im.server.api.message.missed;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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
                        .responseSingle((rsp, buf) -> {
                            return buf.switchIfEmpty(
                                            Mono.error(new EMUnknownException("response is null")))
                                    .flatMap(byteBuf -> {
                                        ErrorMapper mapper = new DefaultErrorMapper();
                                        mapper.statusCode(rsp);
                                        mapper.checkError(byteBuf);
                                        return Mono.just(byteBuf);
                                    });
                        }))
                .map(buf -> {
                    MessageMissedCountResponse response =
                            this.context.getCodec().decode(buf, MessageMissedCountResponse.class);
                    buf.release();
                    return response;
                })
                .flatMapIterable(MessageMissedCountResponse::getMissedMessageCounts);
    }
}
