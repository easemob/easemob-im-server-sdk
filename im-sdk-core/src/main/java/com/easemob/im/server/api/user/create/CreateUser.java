package com.easemob.im.server.api.user.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.get.UserGetResponse;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Mono;

public class CreateUser {

    private Context context;

    public CreateUser(Context context) {
        this.context = context;
    }

    public Mono<EMUser> single(String username, String password) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/users")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateUserRequest(username, password)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, UserGetResponse.class))
                .handle((rsp, sink) -> {
                    EMUser user = rsp.getEMUser(username);
                    if (user == null) {
                        sink.error(new EMUnknownException(String.format("user:%s", username)));
                        return;
                    }
                    sink.next(user);
                });
    }

}
