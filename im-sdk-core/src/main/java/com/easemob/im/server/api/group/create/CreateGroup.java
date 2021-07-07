package com.easemob.im.server.api.group.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.List;

public class CreateGroup {

    private Context context;

    public CreateGroup(Context context) {
        this.context = context;
    }

    public Mono<String> publicGroup(String owner, String groupName, String description,
            List<String> members, int maxMembers, boolean needApproveToJoin) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, true, owner,
                                        members, maxMembers, false, needApproveToJoin)))))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String groupId = rsp.getGroupId();
                    if (groupId == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(groupId);
                });
    }

    public Mono<String> privateGroup(String owner, String groupName, String description,
            List<String> members, int maxMembers, boolean canMemberInvite) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, false, owner,
                                        members, maxMembers, canMemberInvite, !canMemberInvite)))))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String groupId = rsp.getGroupId();
                    if (groupId == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(groupId);
                });
    }
}
