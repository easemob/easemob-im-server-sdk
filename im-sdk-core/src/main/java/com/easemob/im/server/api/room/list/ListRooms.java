package com.easemob.im.server.api.room.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListRooms {
    
    private Context context;

    public ListRooms(Context context) {
        this.context = context;
    }

    public Flux<String> all(int limit) {
        return next(limit, null)
                .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(limit, rsp.getCursor()))
                .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<String>> next(int limit, String cursor) {
        String uri = String.format("/chatrooms?limit=%d", limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }
        return this.context.getHttpClient()
                .get()
                .uri(uri)
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomsResponse.class))
                .map(ListRoomsResponse::toEMPage);
    }

    public Flux<String> userJoined(String username) {
        return this.context.getHttpClient()
                .get()
                .uri(String.format("/users/%s/joined_chatrooms", username))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomsResponse.class))
                .flatMapIterable(ListRoomsResponse::getRoomIds);
    }
}
