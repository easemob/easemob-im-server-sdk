package com.easemob.im.server.api.mute.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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
                    GetMuteDetailResponse response =
                            this.context.getCodec().decode(buf, GetMuteDetailResponse.class);
                    buf.release();
                    return response;
                })
                .map(GetMuteDetailResponse::toEMMute);
    }

}
