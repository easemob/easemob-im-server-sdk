package com.easemob.im.server.api.group.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroupMember;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupMemberList {

    private Context context;

    public GroupMemberList(Context context) {
        this.context = context;
    }

    public Flux<EMGroupMember> all(String groupId, int limit) {
        return next(groupId, limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(groupId, limit, rsp.getCursor()))
            .concatMapIterable(GroupMemberListResponse::getMembers);
    }

    public Mono<GroupMemberListResponse> next(String groupId, int limit, String cursor) {
        String uri = String.format("/chatgroups/%s/users?limit=%d", groupId, limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }

        return this.context.getHttpClient()
            .get()
            .uri(uri)
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupMemberListResponse.class));
    }
}
