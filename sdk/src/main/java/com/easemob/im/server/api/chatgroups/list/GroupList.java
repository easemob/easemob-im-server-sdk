package com.easemob.im.server.api.chatgroups.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupList {

    private Context context;

    public GroupList(Context context) {
        this.context = context;
    }

    /**
     * List all groups.
     *
     * @param limit how many groups retrieved each round trip
     * @return A {@code Flux} which emits {@code EMGroup}.
     */
    public Flux<EMGroup> all(int limit) {
        return all(limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : all(limit, rsp.getCursor()))
            .concatMapIterable(GroupListResponse::getGroups);
    }

    /**
     * List all groups.
     *
     * @param limit how many groups retrieved each round trip
     * @param cursor where to continue, returned in previous {@code GroupListResponse}
     * @return A {@code Mono} emits {@code GroupListResponse} if successful
     */
    public Mono<GroupListResponse> all(int limit, String cursor) {
        String path = String.format("/chatgroups?limit=%s", limit);
        if (cursor != null) {
            path = String.format("%s&cursor=%s", path, cursor);
        }
        return this.context.getHttpClient()
            .get()
            .uri(path)
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupListResponse.class));
    }
}
