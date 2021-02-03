package com.easemob.im.server.api.chatgroups.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class GroupCreate {

    private Context context;

    public GroupCreate(Context context) {
        this.context = context;
    }

    /**
     * Create a public group.
     *
     * @param owner the owner's username
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> publicGroup(String owner) {
        return publicGroup(owner, null);
    }

    /**
     * Create a public group.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> publicGroup(String owner, List<String> members) {
        return publicGroup(owner, null, 200);
    }

    /**
     * Create a public group.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @param maxMembers how many members could join this group
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> publicGroup(String owner, List<String> members, int maxMembers) {
        return publicGroup(owner, members, maxMembers, false);
    }

    /**
     * Create a public group. A public group is listed from GroupList api.
     * For now, group member could not invite others to join, you can workaround this by modify the memberCanInvite flag after group created.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @param maxMembers how many members could join this group
     * @param needApproveToJoin whether user joining this group have to wait, until owner/admin approve it
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> publicGroup(String owner, List<String> members, int maxMembers, boolean needApproveToJoin) {
        return this.context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.just(new GroupCreateRequest(true, owner, members, maxMembers, false, needApproveToJoin))
                .map(req -> this.context.getCodec().encode(req)))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupCreateResponse.class))
            .map(GroupCreateResponse::toEMGroup);
    }

    /**
     * Create a private group.
     *
     * @param owner the owner's username
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> privateGroup(String owner) {
        return privateGroup(owner, null);
    }

    /**
     * Create a private group.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> privateGroup(String owner, List<String> members) {
        return privateGroup(owner, members, 200);
    }

    /**
     * Create a private group.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @param maxMembers how many members could join this group
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> privateGroup(String owner, List<String> members, int maxMembers) {
        return privateGroup(owner, members, maxMembers, false);
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
    public Mono<EMGroup> privateGroup(String owner, List<String> members, int maxMembers, boolean canMemberInvite) {
        return this.context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.just(new GroupCreateRequest(false, owner, members, maxMembers, canMemberInvite, !canMemberInvite))
                .map(req -> this.context.getCodec().encode(req)))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupCreateResponse.class))
            .map(GroupCreateResponse::toEMGroup);
    }
}
