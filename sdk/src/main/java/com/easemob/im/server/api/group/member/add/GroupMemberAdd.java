package com.easemob.im.server.api.group.member.add;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class GroupMemberAdd {

    public static Mono<Void> single(Context context, String groupId, String username) {
        return context.getHttpClient()
            .post()
            .uri(String.format("/chatgroups/%s/users/%s", groupId, username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }

}
