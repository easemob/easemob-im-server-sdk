package com.easemob.im.server.api.user.status;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.user.list.UserListResponse;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUserStatus;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

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
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                        .flatMap(tuple2 -> {
                            HttpClientResponse clientResponse = tuple2.getT1();

                            return Mono.defer(() -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(clientResponse);
                                mapper.checkError(tuple2.getT2());
                                return Mono.just(tuple2.getT2());
                            }).onErrorResume(e -> {
                                if (e instanceof EMException) {
                                    return Mono.error(e);
                                }
                                return Mono.error(new EMUnknownException(e.getMessage()));
                            }).flatMap(byteBuf -> {
                                UserStatusResponse userStatusResponse = this.context.getCodec().decode(byteBuf, UserStatusResponse.class);
                                return Mono.just(userStatusResponse.isUserOnline(username));
                            });
                        }));
    }

    public Mono<List<EMUserStatus>> isUsersOnline(List<String> usernames) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/users/batch/status")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new UserStatusBatchQueryRequest(usernames)))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                        .flatMap(tuple2 -> {
                            HttpClientResponse clientResponse = tuple2.getT1();

                            return Mono.defer(() -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(clientResponse);
                                mapper.checkError(tuple2.getT2());
                                return Mono.just(tuple2.getT2());
                            }).onErrorResume(e -> {
                                if (e instanceof EMException) {
                                    return Mono.error(e);
                                }
                                return Mono.error(new EMUnknownException(e.getMessage()));
                            }).flatMap(byteBuf -> {
                                UserStatusBatchQueryResponse userStatusBatchQueryResponse = this.context.getCodec().decode(byteBuf, UserStatusBatchQueryResponse.class);
                                return Mono.just(userStatusBatchQueryResponse.getUsersOnline());
                            });
                        }));
    }
}
