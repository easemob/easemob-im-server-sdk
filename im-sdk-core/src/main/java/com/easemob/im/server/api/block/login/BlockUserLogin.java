package com.easemob.im.server.api.block.login;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class BlockUserLogin {

    public static Mono<Void> blockUser(Context context, String username) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/users/%s/deactivate", username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }

    public static Mono<Void> unblockUser(Context context, String username) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/users/%s/activate", username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }
}
