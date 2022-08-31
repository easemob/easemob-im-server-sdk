package com.easemob.im.server.api.user.get;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public class UserGet {

    private Context context;

    public UserGet(Context context) {
        this.context = context;
    }

    public Mono<EMUser> single(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s", username))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                        .flatMap(tuple2 -> {
                            HttpClientResponse clientResponse = tuple2.getT1();

                            return Mono.defer(() -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(clientResponse);
                                mapper.checkError(tuple2.getT2());
                                return Mono.just(tuple2.getT2());
                            }).onErrorResume(e -> {
                                if (e instanceof EMException) {
                                    return Mono.error(e);
                                }
                                return Mono.error(new EMUnknownException(String.format("user:%s", username)));
                            }).flatMap(byteBuf -> {
                                UserGetResponse userGetResponse = this.context.getCodec()
                                        .decode(byteBuf, UserGetResponse.class);
                                return Mono.just(userGetResponse.getEMUser(username));
                            });
                        }));
    }
}
