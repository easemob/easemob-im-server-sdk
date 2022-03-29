package com.easemob.im.server.api.mute.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMMute;
import reactor.core.publisher.Mono;

import java.util.List;

public class MuteList {

    private Context context;

    public MuteList(Context context) {
        this.context = context;
    }

    public Mono<List<EMMute>> execute() {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri("/mutes")
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, GetMuteListResponse.class))
                .map(rsp -> {
                    List<EMMute> details = rsp.toEMMute();
                    return details;
                });
    }
}
