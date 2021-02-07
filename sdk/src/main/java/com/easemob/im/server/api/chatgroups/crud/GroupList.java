package com.easemob.im.server.api.chatgroups.crud;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupList {

    private Context context;

    public GroupList(Context context) {
        this.context = context;
    }

    public static Flux<EMGroup> all(Context context, int limit) {
        return next(context, limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(context, limit, rsp.getCursor()))
            .concatMapIterable(GroupListResponse::getEMGroups);
    }

    public static Mono<GroupListResponse> next(Context context, int limit, String cursor) {
        String path = String.format("/chatgroups?limit=%s", limit);
        if (cursor != null) {
            path = String.format("%s&cursor=%s", path, cursor);
        }
        return context.getHttpClient()
            .get()
            .uri(path)
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupListResponse.class));
    }

    public static Flux<EMGroup> userJoined(Context context, String username) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/joined_chatgroups", username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupListResponse.class))
            .flatMapIterable(GroupListResponse::getEMGroups);
    }
}
