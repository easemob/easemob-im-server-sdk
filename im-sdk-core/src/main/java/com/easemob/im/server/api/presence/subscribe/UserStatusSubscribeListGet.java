package com.easemob.im.server.api.presence.subscribe;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class UserStatusSubscribeListGet {

    private Context context;

    public UserStatusSubscribeListGet(Context context) {
        this.context = context;
    }

    public Mono<PresenceUserStatusSubscribeResult> execute(String operator, int pageNum, int pageSize) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .get()
                        .uri(String.format("/users/%s/presence/sublist?pageNum=%d&pageSize=%d", operator, pageNum, pageSize))
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
                    PresenceUserStatusSubscribeListGetResponse response =
                            this.context.getCodec().decode(buf, PresenceUserStatusSubscribeListGetResponse.class);
                    buf.release();
                    return response;
                })
                .map(PresenceUserStatusSubscribeListGetResponse::getResult);
    }
}
