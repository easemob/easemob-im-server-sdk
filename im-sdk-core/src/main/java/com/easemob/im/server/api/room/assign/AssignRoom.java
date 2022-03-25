package com.easemob.im.server.api.room.assign;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class AssignRoom {

    private Context context;

    public AssignRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String chatroomId, String newOwner) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/chatrooms/%s", chatroomId))
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
