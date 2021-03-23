package com.easemob.im.server.api.block.room.join;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BlockUserJoinRoom {

    public static Flux<EMBlock> getBlockedUsers(Context context, String roomId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatrooms/%s/blocks/users", roomId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, GetBlockedUsersResponse.class))
                .flatMapIterable(GetBlockedUsersResponse::getUsernames)
                .map(username -> new EMBlock(username, null));
    }

    public static Mono<Void> blockUser(Context context, String username, String roomId) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/chatrooms/%s/blocks/users/%s", roomId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, BlockUserJoinRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public static Mono<Void> unblockUser(Context context, String username, String roomId) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/%s/blocks/users/%s", roomId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, UnblockUserJoinRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
