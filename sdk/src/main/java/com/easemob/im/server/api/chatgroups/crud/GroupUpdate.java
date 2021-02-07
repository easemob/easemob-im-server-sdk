package com.easemob.im.server.api.chatgroups.crud;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class GroupUpdate {
    public static Mono<Void> owner(Context context, String groupId, String owner) {
        return context.getHttpClient()
                .put()
                .uri(String.format("/chatgroups/%s", groupId))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(new GroupUpdateOwnerRequest(owner)))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }
}
