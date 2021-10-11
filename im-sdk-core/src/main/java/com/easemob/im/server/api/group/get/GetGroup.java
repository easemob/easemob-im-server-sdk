package com.easemob.im.server.api.group.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Mono;

public class GetGroup {
    private Context context;

    public GetGroup(Context context) {
        this.context = context;
    }

    public Mono<EMGroup> execute(String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s", groupId))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, GetGroupResponse.class))
                .map(rsp -> {
                    EMGroup detail = rsp.toGroupDetail(groupId);
                    if (detail == null) {
                        throw new EMNotFoundException(String.format("group:%s", groupId));
                    }
                    return detail;
                });
    }
}
