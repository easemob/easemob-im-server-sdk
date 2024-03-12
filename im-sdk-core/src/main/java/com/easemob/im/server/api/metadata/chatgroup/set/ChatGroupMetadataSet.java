package com.easemob.im.server.api.metadata.chatgroup.set;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ChatGroupMetadataSet {
    private Context context;

    public ChatGroupMetadataSet(Context context) {
        this.context = context;
    }

    public Mono<Void> toChatGroupUser(String username, String groupId,
            Map<String, String> metadata) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .put()
                        .uri(String.format("/metadata/chatgroup/%s/user/%s", groupId, username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ChatGroupMetadataSetRequest(metadata)))))
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
                .doOnSuccess(ReferenceCounted::release)
                .then();
    }
}
