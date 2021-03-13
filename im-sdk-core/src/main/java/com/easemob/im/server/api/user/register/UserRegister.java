package com.easemob.im.server.api.user.register;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Mono;

public class UserRegister {

    public static Mono<EMUser> single(Context context, String username, String password) {
        return context.getHttpClient()
            .post()
            .uri("/users")
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new UserRegisterRequest(username, password)))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, UserRegisterResponse.class))
            .map(responseIgnored -> new EMUser(username));
    }

}
