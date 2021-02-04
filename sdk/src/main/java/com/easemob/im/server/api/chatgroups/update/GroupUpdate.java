package com.easemob.im.server.api.chatgroups.update;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class GroupUpdate {
    private Context context;

    private String groupId;

    private GroupUpdateRequest request;

    public GroupUpdate(Context context, String groupId, GroupUpdateRequest request) {
        this.context = context;
        this.groupId = groupId;
        this.request = request;
    }

    public Mono<Void> execute() {
        return this.context.getHttpClient()
            .put()
            .uri(String.format("/chatgroups/%s", this.groupId))
            .send(Mono.just(this.context.getCodec().encode(this.request)))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupUpdateResponse.class))
            .doOnNext(rsp -> {
                if (this.request.getMaxMembers() != null && (rsp.getMaxMembersUpdated() == null || !rsp.getMaxMembersUpdated())) {
                    throw new EMUnknownException("maxMembers");
                }

                if (this.request.getMemberCanInviteOthers() != null && (rsp.getMemberCanInviteOthersUpdated() == null || !rsp.getMemberCanInviteOthersUpdated())) {
                    throw new EMUnknownException("memberCanInviteOthers");
                }

                if (this.request.getNeedApproveToJoin() != null && (rsp.getNeedApproveToJoinUpdated() == null || !rsp.getNeedApproveToJoinUpdated())) {
                    throw new EMUnknownException("needApproveToJoin");
                }
            })
            .then();
    }
}
