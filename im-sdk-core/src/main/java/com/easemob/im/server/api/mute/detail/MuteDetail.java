package com.easemob.im.server.api.mute.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMMute;
import reactor.core.publisher.Mono;

public class MuteDetail {

    private Context context;

    public MuteDetail(Context context) {
        this.context = context;
    }

    public Mono<EMMute> execute(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/mutes/%s", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, GetMuteDetailResponse.class))
                .map(rsp -> {
                    EMMute detail = rsp.toEMMute();
                    return detail;
                });
    }

}
