package com.easemob.im.server.api.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.register.UserRegisterRequestV1;
import com.easemob.im.server.api.user.register.UserRegisterResponse;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 用户注册
 */
public class UserRegister {
    private Context context;

    UserRegister(Context context) {
        this.context = context;
    }

    /**
     * 注册单个用户
     * @param request
     * @return
     */
    public Mono<EMUser> single(UserRegisterRequestV1 request) {
        return Mono.from(register(Mono.just(request)));
    }

    /**
     * 逐个注册多个用户
     * @param requests
     * @return
     */
    public Flux<EMUser> each(Flux<UserRegisterRequestV1> requests) {
        return Flux.from(register(requests)).limitRate(1);
    }

    private Publisher<EMUser> register(Publisher<UserRegisterRequestV1> requests) {
        return Flux.from(requests)
            .concatMap(req -> this.context.getHttpClient()
                .headersWhen(this.context.getBearerAuthorization())
                .post()
                .uri("/users")
                .send(Mono.just(this.context.getCodec().encode(req)))
                .responseSingle((rsp, buf) -> {
                    if (!rsp.status().equals(HttpResponseStatus.OK)) {
                        return Mono.error(new EMUnknownException(rsp.toString()));
                    }
                    return buf;
                }).map(buf -> this.context.getCodec().decode(buf, UserRegisterResponse.class))
                .map(responseIgnored -> new EMUser(req.getUsername())));
    }

}
