package com.easemob.im.server.api.chatrooms.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DeleteRoom {

    public static Mono<Void> byId(Context context, String roomId) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/%s", roomId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, DeleteRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

}
