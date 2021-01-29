package com.easemob.im.server.api.user.password;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class UserPassword {
    private Context context;

    public UserPassword(Context context) {
        this.context = context;
    }

    public Mono<Void> reset(String username, String password) {
        return this.context.getHttpClient()
            .put()
            .uri(String.format("/users/%s/password", username))
            .send(Mono.just(this.context.getCodec().encode(new UserPasswordResetRequest(password))))
            .response()
            .flatMap(r -> this.context.getErrorMapper().apply(r)) // TODO: 测试使用responseSingle 如果返回404 会不会报错！
            .then();
    }
}
