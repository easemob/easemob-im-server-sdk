package com.easemob.im.server.api.room.superadmin.promote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class PromoteRoomSuperAdmin {

    private Context context;

    public PromoteRoomSuperAdmin(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username) {
        return this.context.getHttpClient()
                .post()
                .uri("/chatrooms/super_admin")
                .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new PromoteRoomSuperAdminRequest(username)))))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, PromoteRoomSuperAdminResponse.class))
                .handle((rsp, sink) -> {
                    if(!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
