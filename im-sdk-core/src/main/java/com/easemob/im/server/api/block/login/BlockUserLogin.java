package com.easemob.im.server.api.block.login;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class BlockUserLogin {

    private Context context;

    public BlockUserLogin(Context context) {
        this.context = context;
    }

    public Mono<Void> blockUser(String username) {
        return this.context.getHttpClient()
                .flatMap(HttpClient -> HttpClient.post()
                        .uri(String.format("/users/%s/deactivate", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then()));
    }

    public Mono<Void> unblockUser(String username) {
        return this.context.getHttpClient()
                .flatMap(HttpClient -> HttpClient.post()
                        .uri(String.format("/users/%s/activate", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then()));
    }
}
