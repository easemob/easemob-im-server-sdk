package com.easemob.im.server.api.metadata.chatroom.set;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.metadata.chatroom.AutoDelete;
import com.easemob.im.server.api.metadata.chatroom.ChatRoomMetadataRequest;
import com.easemob.im.server.api.user.get.UserGetResponse;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

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
                        return Mono.error(new EMUnknownException(String.format("chatroomId:%s", chatroomId)));
                    }).flatMap(byteBuf -> {
                        ChatRoomMetadataSetResponse chatRoomMetadataSetResponse = this.context.getCodec().decode(byteBuf, ChatRoomMetadataSetResponse.class);
                        return Mono.just(chatRoomMetadataSetResponse);
                    });
                }));
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
                                return Mono.error(new EMUnknownException(String.format("chatroomId:%s", chatroomId)));
                            }).flatMap(byteBuf -> {
                                ChatRoomMetadataSetResponse chatRoomMetadataSetResponse = this.context.getCodec().decode(byteBuf, ChatRoomMetadataSetResponse.class);
                                return Mono.just(chatRoomMetadataSetResponse);
                            });
                        }));
    }
}
