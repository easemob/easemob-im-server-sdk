package com.easemob.im.server.api.presence.online;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class UserOnlineCountGet {

    private Context context;

    public UserOnlineCountGet(Context context) {
        this.context = context;
    }

    public Mono<Integer> execute(String id, int type) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .get()
                        .uri(String.format("/presence/online/%s/type/%d", id, type))
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
                    PresenceUserOnlineCountResponse response =
                            this.context.getCodec().decode(buf, PresenceUserOnlineCountResponse.class);
                    buf.release();
                    return response;
                })
                .map(PresenceUserOnlineCountResponse::getResult);
    }
}
