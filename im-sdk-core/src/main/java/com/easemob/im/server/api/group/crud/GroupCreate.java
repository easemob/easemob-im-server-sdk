package com.easemob.im.server.api.group.crud;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.List;

public class GroupCreate {

    private Context context;

    public GroupCreate(Context context) {
        this.context = context;
    }

    public Mono<String> publicGroup(String owner, List<String> members, int maxMembers, boolean needApproveToJoin) {
        return this.context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new GroupCreateRequest(true, owner, members, maxMembers, false, needApproveToJoin)))))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupCreateResponse.class))
            .handle((rsp, sink) -> {
                String groupId = rsp.getGroupId();
                if (groupId == null) {
                    sink.error(new EMUnknownException("groupId is null"));
                }
                sink.next(groupId);
            });
    }

    public Mono<String> privateGroup(String owner, List<String> members, int maxMembers, boolean canMemberInvite) {
        return this.context.getHttpClient()
            .post()
            .uri("/chatgroups")
            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new GroupCreateRequest(false, owner, members, maxMembers, canMemberInvite, !canMemberInvite)))))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupCreateResponse.class))
            .handle((rsp, sink) -> {
                String groupId = rsp.getGroupId();
                if (groupId == null) {
                    sink.error(new EMUnknownException("groupId is null"));
                }
                sink.next(groupId);
            });
    }
}
