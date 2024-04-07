package com.easemob.im.server.api.message.roaming;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

public class OneWayClearUserWithinPeriod {
    private final Context context;

    public OneWayClearUserWithinPeriod(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String username, String clearUser, long time) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/rest/message/roaming/chat/user/%s/time?userId=%s&delTime=%d", username, clearUser, time))
                        .responseSingle((rsp, buf) -> {
                            return buf.switchIfEmpty(
                                            Mono.error(new EMUnknownException("response is null")))
                                    .flatMap(byteBuf -> {
                                        ErrorMapper mapper = new DefaultErrorMapper();
                                        mapper.statusCode(rsp);
                                        mapper.checkError(byteBuf);
                                        return Mono.just(byteBuf);
                                    });
                        }))
                .doOnSuccess(ReferenceCounted::release)
                .then();
    }
}
