package com.easemob.im.server.api.group.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
                .then();
    }
}
