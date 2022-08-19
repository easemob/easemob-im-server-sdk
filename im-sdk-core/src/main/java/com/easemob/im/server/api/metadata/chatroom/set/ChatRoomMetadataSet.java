package com.easemob.im.server.api.metadata.chatroom.set;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.chatroom.AutoDelete;
import com.easemob.im.server.api.metadata.chatroom.ChatRoomMetadataRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ChatRoomMetadataSet {

    private Context context;

    public ChatRoomMetadataSet(Context context) {
        this.context = context;
    }

    public Mono<ChatRoomMetadataSetResponse> toChatRoom(String operator,String chatroomId,
            Map<String, String> metadata,
            AutoDelete autoDelete) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).put()
                        .uri(String.format("/metadata/chatroom/%s/user/%s", chatroomId, operator))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatRoomMetadataRequest(metadata, autoDelete)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ChatRoomMetadataSetResponse.class));
    }

    public Mono<ChatRoomMetadataSetResponse> toChatRoomForced(String operator,String chatroomId,
            Map<String, String> metadata,
            AutoDelete autoDelete) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).put()
                        .uri(String.format("/metadata/chatroom/%s/user/%s/forced", chatroomId,
                                operator))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatRoomMetadataRequest(metadata, autoDelete)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ChatRoomMetadataSetResponse.class));
    }
}
