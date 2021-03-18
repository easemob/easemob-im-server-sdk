package com.easemob.im.server.api.group.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Mono;

// TODO: move GroupDetails into group settings
public class GroupDetails {

    public static Mono<EMGroup> execute(Context context, String groupId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatgroups/%s", groupId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, GroupDetailResponse.class))
                .map(rsp -> {
                    EMGroup detail = rsp.toGroupDetail(groupId);
                    if (detail == null) {
                        throw new EMNotFoundException(String.format("group:%s", groupId));
                    }
                    return detail;
                });
    }
}
