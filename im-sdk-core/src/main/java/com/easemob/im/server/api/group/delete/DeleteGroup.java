package com.easemob.im.server.api.group.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

public class DeleteGroup {
    private Context context;

    public DeleteGroup(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatgroups/%s", groupId))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
                .then();
    }
}
