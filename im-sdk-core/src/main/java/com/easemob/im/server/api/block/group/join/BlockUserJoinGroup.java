package com.easemob.im.server.api.block.group.join;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BlockUserJoinGroup {

    private Context context;

    public BlockUserJoinGroup(Context context) {
        this.context = context;
    }

    public Flux<EMBlock> getBlockedUsers(String groupId) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s/blocks/users", groupId))
                        .responseSingle(
                                (rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> context.getCodec().decode(buf, GetBlockedUsersResponse.class))
                .flatMapIterable(GetBlockedUsersResponse::getUsernames)
                .map(username -> new EMBlock(username, null));
    }

    public Mono<Void> blockUser(String username, String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatgroups/%s/blocks/users/%s", groupId, username))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, BlockUserJoinGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public Mono<Void> unblockUser(String username, String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatgroups/%s/blocks/users/%s", groupId, username))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, UnblockUserJoinGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
