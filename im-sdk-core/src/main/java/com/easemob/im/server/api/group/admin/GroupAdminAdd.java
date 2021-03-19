package com.easemob.im.server.api.group.admin;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class GroupAdminAdd {

    private Context context;

    public GroupAdminAdd(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String groupId, String username) {
        return this.context.getHttpClient()
            .post()
            .uri(String.format("/chatgroups/%s/admin", groupId))
            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new GroupAdminAddRequest(username)))))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then())
            .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty());
    }
}
