package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUserStatus;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserStatus {

    private Context context;

    public UserStatus(Context context) {
        this.context = context;
    }

    public Mono<Boolean> isUserOnline(String username) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/status", username))
                        .responseSingle((rsp, buf) -> {
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
                    UserStatusResponse userStatusResponse =
                            this.context.getCodec().decode(byteBuf, UserStatusResponse.class);
                    return userStatusResponse.isUserOnline(username);
                });
    }

    public Mono<List<EMUserStatus>> isUsersOnline(List<String> usernames) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/users/batch/status")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new UserStatusBatchQueryRequest(usernames)))))
                        .responseSingle((rsp, buf) -> {
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
                    UserStatusBatchQueryResponse userStatusBatchQueryResponse =
                            this.context.getCodec()
                                    .decode(byteBuf, UserStatusBatchQueryResponse.class);
                    return userStatusBatchQueryResponse.getUsersOnline();
                });
    }
}
