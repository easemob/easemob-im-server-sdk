package com.easemob.im.server.api.room.superadmin.demote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DemoteRoomSuperAdmin {

    private Context context;

    public DemoteRoomSuperAdmin(Context context) {
        this.context = context;
    }

    public Mono<Void> singnle(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatrooms/super_admin/%s", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, DemoteRoomSuperAdminResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSucess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
