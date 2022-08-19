package com.easemob.im.server.api.metadata.chatroom.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.chatroom.ChatRoomMetadataRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public class ChatRoomMetadataDelete {

    private Context context;

    public ChatRoomMetadataDelete(Context context) {
        this.context = context;
    }

    public Mono<ChatRoomMetadataDeleteResponse> fromChatRoom(String operator,String chatroomId,
            List<String> keys) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).delete()
                        .uri(String.format("/metadata/chatroom/%s/user/%s", chatroomId, operator))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatRoomMetadataRequest(keys)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec()
                        .decode(buf, ChatRoomMetadataDeleteResponse.class));
    }

    public Mono<ChatRoomMetadataDeleteResponse> fromChatRoomForced(String chatroomId,
            List<String> keys) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).delete()
                        .uri(String.format("/metadata/chatroom/%s/user/admin/forced", chatroomId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatRoomMetadataRequest(keys)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec()
                        .decode(buf, ChatRoomMetadataDeleteResponse.class));
    }

}
