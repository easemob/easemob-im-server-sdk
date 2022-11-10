package com.easemob.im.server.api.mute.mute;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class MuteUser {

    private Context context;

    public MuteUser(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(MuteUserRequest request) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/mutes")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(request))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
                .then();
    }

}
