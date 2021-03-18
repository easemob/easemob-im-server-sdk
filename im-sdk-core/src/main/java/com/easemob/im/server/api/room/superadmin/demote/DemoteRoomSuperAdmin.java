package com.easemob.im.server.api.room.superadmin.demote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DemoteRoomSuperAdmin {

    public static Mono<Void> singnle(Context context, String username) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/super_admin/%s", username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, DemoteRoomSuperAdminResponse.class))
                .handle((rsp, sink) -> {
                    if(!rsp.isSucess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
