package com.easemob.im.server.api.mute.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, GetMuteDetailResponse.class))
                .map(GetMuteDetailResponse::toEMMute);
    }

}
