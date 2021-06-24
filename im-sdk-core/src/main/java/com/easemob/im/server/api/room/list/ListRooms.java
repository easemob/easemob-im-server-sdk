package com.easemob.im.server.api.room.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
import io.netty.handler.codec.http.QueryStringEncoder;
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
        QueryStringEncoder encoder = new QueryStringEncoder("/chatrooms");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomsResponse.class))
                .map(ListRoomsResponse::toEMPage);
    }

    public Flux<String> userJoined(String username) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/joined_chatrooms", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomsResponse.class))
                .flatMapIterable(ListRoomsResponse::getRoomIds);
    }
}
