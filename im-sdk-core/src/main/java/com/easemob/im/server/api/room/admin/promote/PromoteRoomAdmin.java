package com.easemob.im.server.api.room.admin.promote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class PromoteRoomAdmin {

    private Context context;

    public PromoteRoomAdmin(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String roomId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/admin", roomId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new PromoteRoomAdminRequest(username)))))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, PromoteRoomAdminResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

}
