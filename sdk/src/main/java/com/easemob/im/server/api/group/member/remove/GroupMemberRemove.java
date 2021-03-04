package com.easemob.im.server.api.group.member.remove;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class GroupMemberRemove {

    public static Mono<Void> single(Context context, String groupId, String username) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/chatgroups/%s/users/%s", groupId, username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then())
            .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty());
    }
}
