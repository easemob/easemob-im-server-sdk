package com.easemob.im.server.api.user.forcelogout;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import reactor.core.publisher.Mono;

public class UserForceLogout {

    private Context context;

    public UserForceLogout(Context context) {
        this.context = context;
    }

    public Mono<Void> byUsername(String username) {
        return byUsernameAndResource(username, null);
    }

    public Mono<Void> byUsernameAndResource(String username, String resource) {
        String path = String.format("/users/%s/disconnect", username);
        if (resource != null) {
            path = String.format("%s/%s", path, resource);
        }
        return this.context.getHttpClient()
            .get()
            .uri(path)
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, UserForceLogoutResponse.class))
            .doOnNext(rsp -> {
                if (!rsp.isSuccessful()) {
                    throw new EMInternalServerErrorException("unknown");
                }

            }).then();
    }
}
