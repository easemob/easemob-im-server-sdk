package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Mono;

public class UserGet {

    private Context context;

    public UserGet(Context context) {
        this.context = context;
    }

    public Mono<EMUser> single(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    return buf.switchIfEmpty(
                                                    Mono.error(new EMUnknownException("response is null")))
                                            .flatMap(byteBuf -> {
                                                ErrorMapper mapper = new DefaultErrorMapper();
                                                mapper.statusCode(rsp);
                                                mapper.checkError(byteBuf);
                                                return Mono.just(byteBuf);
                                            });
                                }))
                .map(byteBuf -> {
                    UserGetResponse userGetResponse = this.context.getCodec()
                            .decode(byteBuf, UserGetResponse.class);
                    return userGetResponse.getEMUser(username.toLowerCase());
                });
    }
}
