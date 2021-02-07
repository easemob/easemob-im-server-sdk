package com.easemob.im.server.api.chatgroups.crud;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Mono;

import java.util.List;

public class GroupCreate {


    public static Mono<EMGroup> publicGroup(Context context, String owner, List<String> members, int maxMembers, boolean needApproveToJoin) {
        return context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new GroupCreateRequest(true, owner, members, maxMembers, false, needApproveToJoin)))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupCreateResponse.class))
            .map(GroupCreateResponse::toEMGroup);
    }

    public static Mono<EMGroup> privateGroup(Context context, String owner, List<String> members, int maxMembers, boolean canMemberInvite) {
        return context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new GroupCreateRequest(false, owner, members, maxMembers, canMemberInvite, !canMemberInvite)))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupCreateResponse.class))
            .map(GroupCreateResponse::toEMGroup);
    }
}
