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

    public Flux<String> all(String roomId, int limit, String sort) {
        return next(roomId, limit, null, sort)
                .expand(rsp -> rsp.getCursor() == null ?
                        Mono.empty() :
                        next(roomId, limit, rsp.getCursor(), sort))
                .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<String>> next(String roomId, int limit, String cursor, String sort) {
        final String uriPath = String.format("/chatrooms/%s/users", roomId);
        QueryStringEncoder encoder = new QueryStringEncoder(uriPath);
        encoder.addParam("version", "v3");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        if (sort != null) {
            encoder.addParam("sort", sort);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomMembersResponse.class))
                .map(ListRoomMembersResponse::toEMPage);
    }

}
