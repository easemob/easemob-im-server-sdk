package com.easemob.im.server.api.room.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DeleteRoom {

    private Context context;

    public DeleteRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> byId(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatrooms/%s", roomId))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, DeleteRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

}
