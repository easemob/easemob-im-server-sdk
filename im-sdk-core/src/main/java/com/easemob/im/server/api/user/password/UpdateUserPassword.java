package com.easemob.im.server.api.user.password;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import reactor.core.publisher.Mono;

public class UpdateUserPassword {
    private Context context;

    public UpdateUserPassword(Context context) {
        this.context = context;
    }

    public Mono<Void> update(String username, String password) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s/password", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new UserPasswordResetRequest(password)))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .then();
    }
}
