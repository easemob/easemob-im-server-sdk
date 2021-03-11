package com.easemob.im.server.api.group.settings;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class GroupSettings {

    public static Mono<Void> update(Context context, String groupId, Consumer<GroupSettingsUpdateRequest> customizer) {
        GroupSettingsUpdateRequest request = new GroupSettingsUpdateRequest();
        customizer.accept(request);

        return context.getHttpClient()
            .put()
            .uri(String.format("/chatgroups/%s", groupId))
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(request))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupSettingsUpdateResponse.class))
            .doOnNext(rsp -> {
                if (request.getMaxMembers() != null && (rsp.getMaxMembersUpdated() == null || !rsp.getMaxMembersUpdated())) {
                    throw new EMUnknownException("maxMembers");
                }

                if (request.getCanMemberInviteOthers() != null && (rsp.getMemberCanInviteOthersUpdated() == null || !rsp.getMemberCanInviteOthersUpdated())) {
                    throw new EMUnknownException("memberCanInviteOthers");
                }

                if (request.getNeedApproveToJoin() != null && (rsp.getNeedApproveToJoinUpdated() == null || !rsp.getNeedApproveToJoinUpdated())) {
                    throw new EMUnknownException("needApproveToJoin");
                }
            })
            .then();
    }
}
