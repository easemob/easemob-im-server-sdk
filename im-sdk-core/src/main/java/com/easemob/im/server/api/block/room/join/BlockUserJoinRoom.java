package com.easemob.im.server.api.block.room.join;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BlockUserJoinRoom {
    
    private Context context;

    public BlockUserJoinRoom(Context context) {
        this.context = context;
    }

    public Flux<EMBlock> getBlockedUsers(String roomId) {
        return this.context.getHttpClient()
                .get()
                .uri(String.format("/chatrooms/%s/blocks/users", roomId))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, GetBlockedUsersResponse.class))
                .flatMapIterable(GetBlockedUsersResponse::getUsernames)
                .map(username -> new EMBlock(username, null));
    }

    public Mono<Void> blockUser(String username, String roomId) {
        return this.context.getHttpClient()
                .post()
                .uri(String.format("/chatrooms/%s/blocks/users/%s", roomId, username))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, BlockUserJoinRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public Mono<Void> unblockUser(String username, String roomId) {
        return this.context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/%s/blocks/users/%s", roomId, username))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, UnblockUserJoinRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
