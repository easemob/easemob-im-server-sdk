package com.easemob.im.server.api.room.admin.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;

public class ListRoomAdmins {
    
    private Context context;

    public ListRoomAdmins(Context context) {
        this.context = context;
    }

    public Flux<String> all(String roomId) {
        return this.context.getHttpClient()
                .get()
                .uri(String.format("/chatrooms/%s/admin", roomId))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomAdminsResponse.class))
                .flatMapIterable(ListRoomAdminsResponse::getAdmins);
    }
}
