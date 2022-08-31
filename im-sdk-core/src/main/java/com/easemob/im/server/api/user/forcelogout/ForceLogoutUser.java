package com.easemob.im.server.api.user.forcelogout;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

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
        // immutable final uri path string
        final String finalPath = String.valueOf(path);
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(finalPath)
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
                                return Mono.error(new EMUnknownException(e.getMessage()));
                            }).flatMap(byteBuf -> {
                                UserForceLogoutResponse userForceLogoutResponse = this.context.getCodec().decode(byteBuf, UserForceLogoutResponse.class);
                                if (!userForceLogoutResponse.isSuccessful()) {
                                    return Mono.error(new EMInternalServerErrorException("unknown"));
                                }
                                return Mono.just(userForceLogoutResponse);
                            }).then();
                        }));

    }
}
