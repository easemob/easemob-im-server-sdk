package com.easemob.im.server.api.chatrooms.admin.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;

public class ListRoomAdmins {
    public static Flux<String> all(Context context, String roomId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatrooms/%s/admin", roomId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, ListRoomAdminsResponse.class))
                .flatMapIterable(ListRoomAdminsResponse::getAdmins);
    }
}
