package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserGet {

    public static Mono<EMUser> single(Context context, String username) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/users/%s", username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, UserGetResponse.class))
            .handle((rsp, sink) -> {
                EMUser user = rsp.getEMUser(username);
                if (user == null) {
                    sink.error(new EMUnknownException(String.format("user:%s", username)));
                    return;
                }
                sink.next(user);
            });
    }



}
