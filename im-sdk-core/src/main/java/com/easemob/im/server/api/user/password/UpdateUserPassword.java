package com.easemob.im.server.api.user.password;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class UpdateUserPassword {
    private Context context;

    public UpdateUserPassword(Context context) {
        this.context = context;
    }

    public Mono<Void> update(String username, String password) {
        return this.context.getHttpClient()
                .flatMap(HttpClient -> HttpClient.put()
                        .uri(String.format("/users/%s/password", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new UserPasswordResetRequest(password)))))
                        .response()
                        .flatMap(r -> this.context.getErrorMapper().apply(r))
                        .then());
    }
}
