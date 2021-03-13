package com.easemob.im.server.api.room.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListRooms {

    public static Flux<String> all(Context context, int limit) {
        return next(context, limit, null)
                .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(context, limit, rsp.getCursor()))
                .concatMapIterable(rsp -> rsp.getRoomIds());
    }

    public static Mono<ListRoomsResponse> next(Context context, int limit, String cursor) {
        String uri = String.format("/chatrooms?limit=%d", limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }
        return context.getHttpClient()
                .get()
                .uri(uri)
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, ListRoomsResponse.class));
    }

    public static Flux<String> userJoined(Context context, String username) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/users/%s/joined_chatrooms", username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, ListRoomsResponse.class))
                .flatMapIterable(ListRoomsResponse::getRoomIds);
    }
}
