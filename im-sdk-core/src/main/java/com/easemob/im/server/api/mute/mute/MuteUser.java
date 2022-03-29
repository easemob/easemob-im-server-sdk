package com.easemob.im.server.api.mute.mute;

import com.easemob.im.server.api.Context;
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
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
                .then();
    }

}
