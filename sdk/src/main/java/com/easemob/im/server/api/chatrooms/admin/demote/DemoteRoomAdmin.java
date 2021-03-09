package com.easemob.im.server.api.chatrooms.admin.demote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DemoteRoomAdmin {

    public static Mono<Void> single(Context context, String roomId, String username) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/%s/admin/%s", roomId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, DemoteRoomAdminResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
