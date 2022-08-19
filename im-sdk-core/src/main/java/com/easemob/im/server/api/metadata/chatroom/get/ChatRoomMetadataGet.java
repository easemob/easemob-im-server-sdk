package com.easemob.im.server.api.metadata.chatroom.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.chatroom.ChatRoomMetadataRequest;
import reactor.core.publisher.Mono;

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
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec()
                        .decode(buf, ChatRoomMetadataGetResponse.class));
    }

    public Mono<ChatRoomMetadataGetResponse> listChatRoomMetadataAll(String chatroomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).post()
                        .uri(String.format("/metadata/chatroom/%s", chatroomId))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec()
                        .decode(buf, ChatRoomMetadataGetResponse.class));
    }
}
