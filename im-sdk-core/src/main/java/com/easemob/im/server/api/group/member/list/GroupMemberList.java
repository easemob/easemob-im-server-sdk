package com.easemob.im.server.api.group.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupMemberList {

    private Context context;

    public GroupMemberList(Context context) {
        this.context = context;
    }

    public Flux<String> all(String groupId, int limit) {
        return next(groupId, limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(groupId, limit, rsp.getCursor()))
            .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<String>> next(String groupId, int limit, String cursor) {
        String uri = String.format("/chatgroups/%s/users?version=v3&limit=%d", groupId, limit);
        if (cursor != null) {
            uri += String.format("&cursor=%s", cursor);
        }

        String finalUri = uri;
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(finalUri)
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, GroupMemberListResponse.class))
                        .map(GroupMemberListResponse::toEMPage));
    }
}
