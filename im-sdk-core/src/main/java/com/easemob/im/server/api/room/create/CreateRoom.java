package com.easemob.im.server.api.room.create;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

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
                                return Mono.error(new EMUnknownException(
                                        String.format("name:%s,description:%s,owner:%s", name,
                                                description, owner)));
                            }).flatMap(byteBuf -> {
                                CreateRoomResponse
                                        createRoomResponse = this.context.getCodec()
                                        .decode(byteBuf, CreateRoomResponse.class);
                                return Mono.just(createRoomResponse.getRoomId());
                            });
                        }));
    }

    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers, String custom) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatrooms")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(CreateRoomRequest
                                        .of(name, description, owner, members, maxMembers,
                                                custom)))))
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
                                return Mono.error(new EMUnknownException(
                                        String.format("name:%s,description:%s,owner:%s", name,
                                                description, owner)));
                            }).flatMap(byteBuf -> {
                                CreateRoomResponse
                                        createRoomResponse = this.context.getCodec()
                                        .decode(byteBuf, CreateRoomResponse.class);
                                return Mono.just(createRoomResponse.getRoomId());
                            });
                        }));
    }

    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers, String custom, Boolean needVerify) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatrooms")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(CreateRoomRequest
                                        .of(name, description, owner, members, maxMembers, custom,
                                                needVerify)))))
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
                                return Mono.error(new EMUnknownException(
                                        String.format("name:%s,description:%s,owner:%s", name,
                                                description, owner)));
                            }).flatMap(byteBuf -> {
                                CreateRoomResponse
                                        createRoomResponse = this.context.getCodec()
                                        .decode(byteBuf, CreateRoomResponse.class);
                                return Mono.just(createRoomResponse.getRoomId());
                            });
                        }));
    }

}
