package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMUserStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserStatus {

    public static Mono<EMUserStatus> single(Context context, String username) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/status", username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, UserStatusResponse.class))
            .flatMapIterable(UserStatusResponse::getUserStatusList)
            .next();
    }

    public static Flux<EMUserStatus> batch(Context context, Flux<String> usernames) {
        final int MAX_USERNAMES = 100;
        return usernames.window(MAX_USERNAMES)
            .concatMap(ns -> ns.collectList()
                .flatMapMany(nsl -> context.getHttpClient()
                    .post()
                    .uri("/users/batch/status")
                    .send(Mono.just(context.getCodec().encode(new UserStatusRequest(nsl))))
                    .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                    .map(buf -> context.getCodec().decode(buf, UserStatusResponse.class))
                    .flatMapIterable(UserStatusResponse::getUserStatusList)));

    }

}
