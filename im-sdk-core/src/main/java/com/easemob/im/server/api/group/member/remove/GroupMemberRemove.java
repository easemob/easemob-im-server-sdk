package com.easemob.im.server.api.group.member.remove;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class GroupMemberRemove {

    private Context context;

    public GroupMemberRemove(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String groupId, String username) {
        return this.context.getHttpClient()
            .delete()
            .uri(String.format("/chatgroups/%s/users/%s", groupId, username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then())
            .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty());
    }
}
