package com.easemob.im.server.api.metadata.chatgroup.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMMetadataBatch;
import reactor.core.publisher.Mono;

import java.util.List;

public class ChatGroupMetadataBatchGet {
    private Context context;

    public ChatGroupMetadataBatchGet(Context context) {
        this.context = context;
    }

    public Mono<EMMetadataBatch> fromChatGroupUsers(String groupId, List<String> targets, List<String> properties) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri(String.format("/metadata/chatgroup/%s/get", groupId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatGroupMetadataBatchGetRequest(targets, properties)))))
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
                .map(buf -> this.context.getCodec().decode(buf, ChatGroupMetadataBatchGetResponse.class))
                .map(ChatGroupMetadataBatchGetResponse::toMetadataBatch);
    }
}
