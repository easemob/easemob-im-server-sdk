package com.easemob.im.server.api.block.room.msg.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BlockUserSendMsgToRoom {

    public static Mono<Void> single(Context context, String username, String roomId, Duration duration) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/chatrooms/%s/mute", roomId))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(BlockUserSendMsgToRoomRequest.of(username, duration)))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, BlockUserSendMsgToRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
