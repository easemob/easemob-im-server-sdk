package com.easemob.im.server.api.group.admin;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;

public class GroupAdminList {

    private Context context;

    public GroupAdminList(Context context) {
        this.context = context;
    }

    public Flux<String> all(String groupId) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s/admin", groupId))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, GroupAdminListResponse.class))
                .flatMapIterable(GroupAdminListResponse::getAdmins);
    }

}
