package com.easemob.im.server.api.group.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupList {

    private Context context;

    public GroupList(Context context) {
        this.context = context;
    }

    public Flux<String> all(int limit) {
        return next(limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(limit, rsp.getCursor()))
            .concatMapIterable(GroupListResponse::getGroupIds);
    }

    public Mono<GroupListResponse> next(int limit, String cursor) {
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

    public Flux<String> userJoined(String username) {
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/joined_chatgroups", username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupListResponse.class))
            .flatMapIterable(GroupListResponse::getGroupIds);
    }
}
