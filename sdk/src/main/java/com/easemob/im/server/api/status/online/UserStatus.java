package com.easemob.im.server.api.status.online;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMUserStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserStatus {

    private Context context;

    public UserStatus(Context context) {
        this.context = context;
    }

    public Mono<EMUserStatus> single(String username) {
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/status", username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, UserStatusResponse.class))
            .flatMapIterable(UserStatusResponse::getUserStatusList)
            .next();
    }

    public Flux<EMUserStatus> each(Flux<String> usernames) {
        final int MAX_USERNAMES = 100;
        return usernames.window(MAX_USERNAMES)
            .concatMap(ns -> ns.collectList()
                .flatMapMany(nsl -> this.context.getHttpClient()
                    .post()
                    .uri("/users/batch/status")
                    .send(Mono.just(this.context.getCodec().encode(new UserStatusRequest(nsl))))
                    .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                    .map(buf -> this.context.getCodec().decode(buf, UserStatusResponse.class))
                    .flatMapIterable(UserStatusResponse::getUserStatusList)));

    }

}
