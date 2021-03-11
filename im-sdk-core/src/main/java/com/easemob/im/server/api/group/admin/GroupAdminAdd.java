package com.easemob.im.server.api.group.admin;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class GroupAdminAdd {
    public static Mono<Void> single(Context context, String groupId, String username) {
        return context.getHttpClient()
            .post()
            .uri(String.format("/chatgroups/%s/admin", groupId))
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new GroupAdminAddRequest(username)))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then())
            .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty());
    }
}
