package com.easemob.im.server.api.group.crud;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class GroupUpdate {

    private Context context;

    public GroupUpdate(Context context) {
        this.context = context;
    }

    public Mono<Void> owner(String groupId, String owner) {
        return this.context.getHttpClient()
                .put()
                .uri(String.format("/chatgroups/%s", groupId))
                .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new GroupUpdateOwnerRequest(owner)))))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then());
    }
}
