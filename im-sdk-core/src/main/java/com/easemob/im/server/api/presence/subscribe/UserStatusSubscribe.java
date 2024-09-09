package com.easemob.im.server.api.presence.subscribe;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.presence.PresenceUserStatusSubscribeResource;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserStatusSubscribe {

    private Context context;

    public UserStatusSubscribe(Context context) {
        this.context = context;
    }

    public Mono<List<PresenceUserStatusSubscribeResource>> execute(String operator, List<String> usernames, Long expiry) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri(String.format("/users/%s/presence/%s", operator, expiry))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new PresenceUserStatusSubscribeRequest(usernames)))))
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
                    PresenceUserStatusSubscribeResponse response =
                            this.context.getCodec().decode(buf, PresenceUserStatusSubscribeResponse.class);
                    buf.release();
                    return response;
                })
                .map(PresenceUserStatusSubscribeResponse::getUserStatusSubscribeResources);
    }
}
