package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeleteUser {

    private Context context;

    public DeleteUser(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/users/%s", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(b -> this.context.getCodec().decode(b, UserUnregisterResponse.class))
                .handle((rsp, sink) -> {
                    if (rsp.getError() != null) {
                        sink.error(new EMUnknownException(rsp.getError()));
                        return;
                    }
                    sink.complete();
                });
    }

    public Flux<String> all(int limit) {
        return next(limit, null)
                .expand(rsp -> rsp.getCursor() == null ?
                        Mono.empty() :
                        next(limit, rsp.getCursor()))
                .concatMapIterable(UserUnregisterResponse::getUsernames);
    }

    public Mono<UserUnregisterResponse> next(int limit, String cursor) {
        // try to avoid manually assembling uri string
        QueryStringEncoder encoder = new QueryStringEncoder("/users");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        String uirString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(uirString)
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(b -> this.context.getCodec().decode(b, UserUnregisterResponse.class));
    }
}
