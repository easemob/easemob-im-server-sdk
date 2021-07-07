package com.easemob.im.server.api.group.admin;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class GroupAdminRemove {

    private Context context;

    public GroupAdminRemove(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String groupId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatgroups/%s/admin/%s", groupId, username))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then()));
    }
}
