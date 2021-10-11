package com.easemob.im.server.api.user.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.handler.codec.http.HttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClientResponse;

import java.util.HashMap;
import java.util.Map;

public class CreateUser {

    private Context context;

    public CreateUser(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username, String password) {
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
                .map(buf -> this.context.getCodec().decode(buf, CreateUserResponse.class))
                .handle((rsp, sink) -> {
                    if (rsp.getError() != null) {
                        sink.error(new EMUnknownException(rsp.getError()));
                        return;
                    }
                    sink.complete();
                });
    }

}
