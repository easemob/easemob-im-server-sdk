package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class UserGet {

    private Context context;

    public UserGet(Context context) {
        this.context = context;
    }

    public Mono<UserGetResponse> single(String username) {
        return this.context.getHttpClient()
                .flatMap(HttpClient -> HttpClient.get()
                        .uri(String.format("/users/%s", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, UserGetResponse.class))
                        .handle((rsp, sink) -> {
                            String user = String.valueOf(rsp.getEntities().get("username"));
                            if (user == null) {
                                sink.error(new EMUnknownException(String.format("user:%s", username)));
                                return;
                            }
                            sink.next(rsp);
                        }));
    }
}
