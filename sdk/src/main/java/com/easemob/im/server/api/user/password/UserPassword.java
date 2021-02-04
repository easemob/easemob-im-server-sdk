package com.easemob.im.server.api.user.password;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class UserPassword {
    public static Mono<Void> reset(Context context, String username, String password) {
        return context.getHttpClient()
            .put()
            .uri(String.format("/users/%s/password", username))
            .send(Mono.just(context.getCodec().encode(new UserPasswordResetRequest(password))))
            .response()
            .flatMap(r -> context.getErrorMapper().apply(r))
            .then();
    }
}
