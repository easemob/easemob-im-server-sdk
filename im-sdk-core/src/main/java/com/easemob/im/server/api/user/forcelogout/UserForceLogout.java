package com.easemob.im.server.api.user.forcelogout;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import reactor.core.publisher.Mono;

public class UserForceLogout {

    public static Mono<Void> byUsername(Context context, String username) {
        return byUsernameAndResource(context, username, null);
    }

    public static Mono<Void> byUsernameAndResource(Context context, String username, String resource) {
        String path = String.format("/users/%s/disconnect", username);
        if (resource != null) {
            path = String.format("%s/%s", path, resource);
        }
        return context.getHttpClient()
            .get()
            .uri(path)
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, UserForceLogoutResponse.class))
            .handle((rsp, sink) -> {
                if (!rsp.isSuccessful()) {
                    sink.error(new EMInternalServerErrorException("unknown"));
                    return;
                }
                sink.complete();
            });
    }
}
