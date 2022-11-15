package com.easemob.im.server.api.group.create;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String groupId = rsp.getGroupId();
                    if (groupId == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(groupId);
                });
    }

    public Mono<String> publicGroup(String owner, String groupName, String description,
            List<String> members, int maxMembers, boolean needApproveToJoin, String custom) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, true, owner,
                                        members, maxMembers, false, needApproveToJoin, custom)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String groupId = rsp.getGroupId();
                    if (groupId == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(groupId);
                });
    }

    public Mono<String> publicGroup(String owner, String groupName, String description,
            List<String> members, int maxMembers, boolean needApproveToJoin, String custom, boolean needVerify) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, true, owner,
                                        members, maxMembers, false, needApproveToJoin, custom, needVerify)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String groupId = rsp.getGroupId();
                    if (groupId == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(groupId);
                });
    }

    public Mono<String> publicGroup(String groupId, String owner, String groupName, String description,
            List<String> members, int maxMembers, boolean needApproveToJoin, String custom, boolean needVerify) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupId, groupName, description, true, owner,
                                        members, maxMembers, false, needApproveToJoin, custom, needVerify)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String id = rsp.getGroupId();
                    if (id == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(id);
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
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
            List<String> members, int maxMembers, boolean canMemberInvite, String custom) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, false, owner,
                                        members, maxMembers, canMemberInvite, !canMemberInvite, custom)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
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
            List<String> members, int maxMembers, boolean canMemberInvite, boolean needInviteConfirm, boolean needApproveToJoin, String custom) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, false, owner,
                                        members, maxMembers, canMemberInvite, needInviteConfirm, needApproveToJoin, custom)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
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
            List<String> members, int maxMembers, boolean canMemberInvite, boolean needInviteConfirm,
            boolean needApproveToJoin, String custom, boolean needVerify) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupName, description, false, owner,
                                        members, maxMembers, canMemberInvite, needInviteConfirm, needApproveToJoin, custom, needVerify)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String groupId = rsp.getGroupId();
                    if (groupId == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(groupId);
                });
    }

    public Mono<String> privateGroup(String groupId, String owner, String groupName, String description,
            List<String> members, int maxMembers, boolean canMemberInvite, boolean needInviteConfirm,
            boolean needApproveToJoin, String custom, boolean needVerify) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/chatgroups")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CreateGroupRequest(groupId, groupName, description, false, owner,
                                        members, maxMembers, canMemberInvite, needInviteConfirm, needApproveToJoin, custom, needVerify)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, CreateGroupResponse.class))
                .handle((rsp, sink) -> {
                    String id = rsp.getGroupId();
                    if (id == null) {
                        sink.error(new EMUnknownException("groupId is null"));
                    }
                    sink.next(id);
                });
    }
}
