package com.easemob.im.server.api.group.assign;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class AssignGroup {

    private Context context;

    public AssignGroup(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String groupId, String newOwner) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/chatgroups/%s", groupId))
                        .send(Mono.create(sink -> {
                            Map<String, Object> paramsMap = new HashMap<>();
                            paramsMap.put("newowner", newOwner);
                            sink.success(this.context.getCodec().encode(paramsMap));
                        }))
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
