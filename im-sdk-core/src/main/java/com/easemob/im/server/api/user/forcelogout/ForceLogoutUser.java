package com.easemob.im.server.api.user.forcelogout;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import com.easemob.im.server.exception.EMUnknownException;
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
        // immutable final uri path string
        final String finalPath = String.valueOf(path);
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(finalPath)
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
                .flatMap(byteBuf -> {
                    UserForceLogoutResponse userForceLogoutResponse =
                            this.context.getCodec().decode(byteBuf, UserForceLogoutResponse.class);
                    if (!userForceLogoutResponse.isSuccessful()) {
                        return Mono.error(new EMInternalServerErrorException("unknown"));
                    }
                    byteBuf.release();
                    return Mono.just(userForceLogoutResponse);
                })
                .then();

    }
}
