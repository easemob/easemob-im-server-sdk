package com.easemob.im.server.api.block.room.msg.unblock;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class UnblockUserSendMsgToRoom {
    public static Mono<Void> single(Context context, String username, String roomId) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/%s/mute/%s", roomId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, UnblockUserSendMsgToRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
