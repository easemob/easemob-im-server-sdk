package com.easemob.im.server.api.room.superadmin.promote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class PromoteRoomSuperAdmin {

    public static Mono<Void> single(Context context, String username) {
        return context.getHttpClient()
                .post()
                .uri("/chatrooms/super_admin")
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(new PromoteRoomSuperAdminRequest(username)))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, PromoteRoomSuperAdminResponse.class))
                .handle((rsp, sink) -> {
                    if(!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
