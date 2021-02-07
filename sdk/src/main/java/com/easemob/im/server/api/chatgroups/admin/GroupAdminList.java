package com.easemob.im.server.api.chatgroups.admin;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroupAdmin;
import reactor.core.publisher.Flux;

public class GroupAdminList {

    public static Flux<EMGroupAdmin> all(Context context, String groupId) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/chatgroups/%s/admin", groupId))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupAdminListResponse.class))
            .flatMapIterable(GroupAdminListResponse::getAdmins)
            .map(username -> new EMGroupAdmin(username, groupId));
    }

}
