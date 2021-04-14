package com.easemob.im.server.api.room.admin.demote;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DemoteRoomAdmin {

    private Context context;

    public DemoteRoomAdmin(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String roomId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatrooms/%s/admin/%s", roomId, username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, DemoteRoomAdminResponse.class))
                        .handle((rsp, sink) -> {
                            if (!rsp.isSuccess()) {
                                sink.error(new EMUnknownException("unknown"));
                                return;
                            }
                            sink.complete();
                        }));
    }
}
