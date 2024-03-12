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

    public Mono<ChatRoomMetadataSetResponse> toChatRoom(String operator, String chatroomId,
            Map<String, String> metadata,
            AutoDelete autoDelete) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json")).put()
                        .uri(String.format("/metadata/chatroom/%s/user/%s", chatroomId, operator))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatRoomMetadataRequest(metadata, autoDelete)))))
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
                    ChatRoomMetadataSetResponse response = this.context.getCodec()
                            .decode(byteBuf, ChatRoomMetadataSetResponse.class);
                    byteBuf.release();
                    return response;
                });
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
                    ChatRoomMetadataSetResponse response = this.context.getCodec()
                            .decode(byteBuf, ChatRoomMetadataSetResponse.class);
                    byteBuf.release();
                    return response;
                });
    }
}
