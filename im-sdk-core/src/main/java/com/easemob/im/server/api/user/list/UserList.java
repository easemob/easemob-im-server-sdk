package com.easemob.im.server.api.user.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserList {

    public static Flux<EMUser> all(Context context, int limit) {
        return next(context, limit, null)
                .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(context, limit, rsp.getCursor()))
                .limitRate(1)
                .concatMapIterable(UserListResponse::getEMUsers)
                .limitRate(limit);
    }

    public static Mono<UserListResponse> next(Context context, int limit, String cursor) {
        String query = String.format("limit=%d", limit);
        if (cursor != null) {
            query = String.format("%s&cursor=%s", query, cursor);
        }
        return context.getHttpClient()
                .get()
                .uri(String.format("/users?%s", query))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, UserListResponse.class));
    }

}
