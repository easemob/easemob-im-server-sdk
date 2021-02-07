package com.easemob.im.server.api.chatgroups.admin;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class GroupAdminRemove {
    public static Mono<Void> single(Context context, String groupId, String username) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/chatgroups/%s/admin/%s", groupId, username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }
}
