package com.easemob.im.server.api.block.group.join;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


// TODO: add block/unblock user join group in batch
public class BlockUserJoinGroup {

    public static Flux<EMBlock> getBlockedUsers(Context context, String groupId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatgroups/%s/blocks/users", groupId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, GetBlockedUsersResponse.class))
                .flatMapIterable(GetBlockedUsersResponse::getUsernames)
                .map(username -> new EMBlock(username, null));
    }

    public static Mono<Void> blockUser(Context context, String username, String groupId) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/chatgroups/%s/blocks/users/%s", groupId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, BlockUserJoinGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public static Mono<Void> unblockUser(Context context, String username, String groupId) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatgroups/%s/blocks/users/%s", groupId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, BlockUserJoinGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
