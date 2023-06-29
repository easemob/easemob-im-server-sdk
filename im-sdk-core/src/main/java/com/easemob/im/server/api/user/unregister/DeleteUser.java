package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.user.list.UserListResponse;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

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
                                    return buf.switchIfEmpty(
                                                    Mono.error(new EMUnknownException("response is null")))
                                            .flatMap(byteBuf -> {
                                                ErrorMapper mapper = new DefaultErrorMapper();
                                                mapper.statusCode(rsp);
                                                mapper.checkError(byteBuf);
                                                return Mono.just(byteBuf);
                                            });
                                }))
                .then();
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
                                    return buf.switchIfEmpty(
                                                    Mono.error(new EMUnknownException("response is null")))
                                            .flatMap(byteBuf -> {
                                                ErrorMapper mapper = new DefaultErrorMapper();
                                                mapper.statusCode(rsp);
                                                mapper.checkError(byteBuf);
                                                return Mono.just(byteBuf);
                                            });
                                }))
                .map(byteBuf -> {
                    UserUnregisterResponse userUnregisterResponse =
                            this.context.getCodec().decode(byteBuf, UserUnregisterResponse.class);
                    return userUnregisterResponse;
                });
    }
}
