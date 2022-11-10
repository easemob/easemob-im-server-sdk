package com.easemob.im.server.api.group.assign;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
