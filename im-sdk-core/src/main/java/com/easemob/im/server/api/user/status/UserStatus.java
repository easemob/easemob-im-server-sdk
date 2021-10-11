package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class UserStatus {

    private Context context;

    public UserStatus(Context context) {
        this.context = context;
    }

    public Mono<Boolean> isUserOnline(String username) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/status", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                                .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> context.getCodec().decode(buf, UserStatusResponse.class))
                .map(rsp -> rsp.isUserOnline(username));
    }

}
