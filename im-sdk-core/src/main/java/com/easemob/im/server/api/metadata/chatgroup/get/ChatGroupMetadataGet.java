package com.easemob.im.server.api.metadata.chatgroup.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMMetadata;
import reactor.core.publisher.Mono;

public class ChatGroupMetadataGet {
    private Context context;

    public ChatGroupMetadataGet(Context context) {
        this.context = context;
    }

    public Mono<EMMetadata> fromChatGroupUser(String username, String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .get()
                        .uri(String.format("/metadata/chatgroup/%s/user/%s", groupId, username))
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
                .map(buf -> {
                    ChatGroupMetadataGetResponse response =
                            this.context.getCodec().decode(buf, ChatGroupMetadataGetResponse.class);
                    buf.release();
                    return response;
                })
                .map(ChatGroupMetadataGetResponse::toMetadata);
    }
}
