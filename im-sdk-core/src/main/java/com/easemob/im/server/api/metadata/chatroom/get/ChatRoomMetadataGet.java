package com.easemob.im.server.api.metadata.chatroom.get;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.metadata.chatroom.ChatRoomMetadataRequest;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

import java.util.List;

public class ChatRoomMetadataGet {

    private Context context;

    public ChatRoomMetadataGet(Context context) {
        this.context = context;
    }

    public Mono<ChatRoomMetadataGetResponse> listChatRoomMetadata(String chatroomId,
            List<String> keys) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).post()
                        .uri(String.format("/metadata/chatroom/%s", chatroomId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatRoomMetadataRequest(keys)))))
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
                                        String.format("chatroomId:%s", chatroomId)));
                            }).flatMap(byteBuf -> {
                                ChatRoomMetadataGetResponse
                                        chatRoomMetadataGetResponse = this.context.getCodec()
                                        .decode(byteBuf, ChatRoomMetadataGetResponse.class);
                                return Mono.just(chatRoomMetadataGetResponse);
                            });
                        }));
    }

    public Mono<ChatRoomMetadataGetResponse> listChatRoomMetadataAll(String chatroomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).post()
                        .uri(String.format("/metadata/chatroom/%s", chatroomId))
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
                                        String.format("chatroomId:%s", chatroomId)));
                            }).flatMap(byteBuf -> {
                                ChatRoomMetadataGetResponse
                                        chatRoomMetadataGetResponse = this.context.getCodec()
                                        .decode(byteBuf, ChatRoomMetadataGetResponse.class);
                                return Mono.just(chatRoomMetadataGetResponse);
                            });
                        }));
    }
}
