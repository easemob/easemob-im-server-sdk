package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListRoomMembers {

    public static Flux<String> all(Context context, String roomId, int limit) {
        return next(context, roomId, limit, null)
                .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(context, roomId, limit, rsp.getCursor()))
                .concatMapIterable(EMPage::getValues);
    }

    public static Mono<EMPage<String>> next(Context context, String roomId, int limit, String cursor) {
        String uri = String.format("/chatrooms/%s/users?limit=%d", roomId, limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }

        return context.getHttpClient()
                .get()
                .uri(uri)
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, ListRoomMembersResponse.class))
                .map(ListRoomMembersResponse::toEMPage);
    }


}
