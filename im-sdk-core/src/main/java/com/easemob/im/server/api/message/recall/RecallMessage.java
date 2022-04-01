package com.easemob.im.server.api.message.recall;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.List;

public class RecallMessage {
    private final Context context;

    public RecallMessage(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(List<RecallMessageSource> messageRequests) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages/recall")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new RecallMessageRequest(messageRequests)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }
}
