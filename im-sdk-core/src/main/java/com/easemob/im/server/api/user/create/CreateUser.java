package com.easemob.im.server.api.user.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.user.get.UserGetResponse;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMCreateUser;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Mono;

import java.util.List;

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
                                .encode(new CreateUserRequest(username, password, null)))))
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
                    UserGetResponse userGetResponse =
                            this.context.getCodec().decode(byteBuf, UserGetResponse.class);
                    byteBuf.release();
                    return userGetResponse.getEMUser(username.toLowerCase());
                });
    }

    public Mono<EMUser> single(String username, String password, String pushNickname) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/users")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateUserRequest(username, password, pushNickname)))))
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
                    UserGetResponse userGetResponse =
                            this.context.getCodec().decode(byteBuf, UserGetResponse.class);
                    byteBuf.release();
                    return userGetResponse.getEMUser(username.toLowerCase());
                });
    }

    public Mono<List<EMUser>> batch(List<EMCreateUser> createUsers) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/users")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(createUsers))))
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
                    BatchCreateUserResponse batchCreateUserResponse =
                            this.context.getCodec().decode(byteBuf, BatchCreateUserResponse.class);
                    byteBuf.release();
                    return batchCreateUserResponse.toEMUsers();
                });
    }

}
