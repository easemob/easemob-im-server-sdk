package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class UserStatus {

    public static Mono<Boolean> isUserOnline(Context context, String username) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/status", username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, UserStatusResponse.class))
            .map(rsp -> rsp.isUserOnline(username));
    }

}
