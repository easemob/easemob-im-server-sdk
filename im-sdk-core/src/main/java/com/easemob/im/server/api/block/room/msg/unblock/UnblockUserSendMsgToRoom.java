package com.easemob.im.server.api.block.room.msg.unblock;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class UnblockUserSendMsgToRoom {

    private Context context;

    public UnblockUserSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username, String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatrooms/%s/mute/%s", roomId, username))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec()
                        .decode(buf, UnblockUserSendMsgToRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
