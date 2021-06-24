package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListRoomMembers {
    
    private Context context;

    public ListRoomMembers(Context context) {
        this.context = context;
    }

    public Flux<String> all(String roomId, int limit) {
        return next(roomId, limit, null)
                .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(roomId, limit, rsp.getCursor()))
                .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<String>> next(String roomId, int limit, String cursor) {
        final String uriPath = String.format("/chatrooms/%s/users", roomId);
        QueryStringEncoder encoder = new QueryStringEncoder(uriPath);
        encoder.addParam("version", "v3");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomMembersResponse.class))
                .map(ListRoomMembersResponse::toEMPage);
    }


}
