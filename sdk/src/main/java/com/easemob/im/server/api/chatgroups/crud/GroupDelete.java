package com.easemob.im.server.api.chatgroups.crud;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class GroupDelete {
    public static Mono<Void> execute(Context context, String groupId) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/chatgroups/%s", groupId))
            .response()
            .flatMap(rsp -> context.getErrorMapper().apply(rsp))
            .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
            .then();
    }
}
