package com.easemob.im.server.api.user.forcelogout;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import reactor.core.publisher.Mono;

public class ForceLogoutUser {

    private Context context;

    public ForceLogoutUser(Context context) {
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
        String finalPath = path;
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(finalPath)
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, UserForceLogoutResponse.class))
                        .handle((rsp, sink) -> {
                            if (!rsp.isSuccessful()) {
                                sink.error(new EMInternalServerErrorException("unknown"));
                                return;
                            }
                            sink.complete();
                        }));
    }
}
