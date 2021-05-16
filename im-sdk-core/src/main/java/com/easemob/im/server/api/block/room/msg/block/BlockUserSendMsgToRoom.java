package com.easemob.im.server.api.block.room.msg.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BlockUserSendMsgToRoom {
    
    private Context context;

    public BlockUserSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username, String roomId, Duration duration) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/mute", roomId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(BlockUserSendMsgToRoomRequest.of(username, duration)))))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, BlockUserSendMsgToRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
