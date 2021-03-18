package com.easemob.im.server.api.group.crud;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class GroupDestroy {
    private Context context;

    public GroupDestroy(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String groupId) {
        return this.context.getHttpClient()
            .delete()
            .uri(String.format("/chatgroups/%s", groupId))
            .response()
            .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
            .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
            .then();
    }
}
