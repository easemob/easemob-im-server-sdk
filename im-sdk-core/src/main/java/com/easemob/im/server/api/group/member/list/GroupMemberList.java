package com.easemob.im.server.api.group.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroupMember;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupMemberList {

    public static Flux<EMGroupMember> all(Context context, String groupId, int limit) {
        return next(context, groupId, limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(context, groupId, limit, rsp.getCursor()))
            .concatMapIterable(GroupMemberListResponse::getMembers);
    }

    public static Mono<GroupMemberListResponse> next(Context context, String groupId, int limit, String cursor) {
        String uri = String.format("/chatgroups/%s/users?limit=%d", groupId, limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }

        return context.getHttpClient()
            .get()
            .uri(uri)
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupMemberListResponse.class));
    }
}
