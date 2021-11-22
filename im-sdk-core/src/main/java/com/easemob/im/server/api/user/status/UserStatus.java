package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMUserStatus;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserStatus {

    private Context context;

    public UserStatus(Context context) {
        this.context = context;
    }

    public Mono<Boolean> isUserOnline(String username) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/status", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> context.getCodec().decode(buf, UserStatusResponse.class))
                .map(rsp -> rsp.isUserOnline(username));
    }

    public Mono<List<EMUserStatus>> isUsersOnline(List<String> usernames) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/users/batch/status")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new UserStatusBatchQueryRequest(usernames)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> context.getCodec().decode(buf, UserStatusBatchQueryResponse.class))
                .map(rsp -> rsp.getUsersOnline());
    }
}
