package com.easemob.im.server.api.message.recall;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .then();
    }

    public Mono<Void> execute(RecallMessageSource recallMessage) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages/msg_recall")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(recallMessage))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }
}
