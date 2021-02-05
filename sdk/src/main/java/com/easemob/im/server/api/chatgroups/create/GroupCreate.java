package com.easemob.im.server.api.chatgroups.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Mono;

import java.util.List;

public class GroupCreate {


    public static Mono<EMGroup> publicGroup(Context context, String owner, List<String> members, int maxMembers, boolean needApproveToJoin) {
        return context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.just(new GroupCreateRequest(true, owner, members, maxMembers, false, needApproveToJoin))
                .map(req -> context.getCodec().encode(req)))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupCreateResponse.class))
            .map(GroupCreateResponse::toEMGroup);
    }

    /**
     * Create a private group. Private group is not listed in GroupList api.
     *
     *
     * @param owner the owner's username
     * @param members the initial members
     * @param maxMembers how many members could join this group
     * @param canMemberInvite can member invite others
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public static Mono<EMGroup> privateGroup(Context context, String owner, List<String> members, int maxMembers, boolean canMemberInvite) {
        return context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.just(new GroupCreateRequest(false, owner, members, maxMembers, canMemberInvite, !canMemberInvite))
                .map(req -> context.getCodec().encode(req)))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupCreateResponse.class))
            .map(GroupCreateResponse::toEMGroup);
    }
}
