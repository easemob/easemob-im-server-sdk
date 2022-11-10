package com.easemob.im.server.api.mute.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, GetMuteListResponse.class))
                .map(GetMuteListResponse::toEMMute);
    }
}
