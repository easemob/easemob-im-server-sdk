package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
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
        String uri = String.format("/chatrooms/%s/users?version=v3&limit=%d", roomId, limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }

        String finalUri = uri;
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(finalUri)
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, ListRoomMembersResponse.class))
                        .map(ListRoomMembersResponse::toEMPage));
    }


}
