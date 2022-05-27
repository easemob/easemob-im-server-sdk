package com.easemob.im.server.api.room.create;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.List;

public class CreateRoom {

    private Context context;

    public CreateRoom(Context context) {
        this.context = context;
    }

    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatrooms")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(CreateRoomRequest
                                        .of(name, description, owner, members, maxMembers)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, CreateRoomResponse.class)
                        .getRoomId());
    }

    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers, String custom) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatrooms")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(CreateRoomRequest
                                        .of(name, description, owner, members, maxMembers, custom)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, CreateRoomResponse.class)
                        .getRoomId());
    }

    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers, String custom, Boolean needVerify) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatrooms")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(CreateRoomRequest
                                        .of(name, description, owner, members, maxMembers, custom, needVerify)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, CreateRoomResponse.class)
                        .getRoomId());
    }

}
