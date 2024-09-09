package com.easemob.im.server.api.presence.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.presence.PresenceUserStatusResource;
import com.easemob.im.server.api.user.status.UserStatusResponse;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserStatusGet {

    private Context context;

    public UserStatusGet(Context context) {
        this.context = context;
    }

    public Mono<List<PresenceUserStatusResource>> execute(String operator, List<String> usernames) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri(String.format("/users/%s/presence", operator))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new PresenceUserStatusGetRequest(usernames)))))
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
                    PresenceUserStatusGetResponse response =
                            this.context.getCodec().decode(buf, PresenceUserStatusGetResponse.class);
                    buf.release();
                    return response;
                })
                .map(PresenceUserStatusGetResponse::getUserStatusResources);
    }

}
