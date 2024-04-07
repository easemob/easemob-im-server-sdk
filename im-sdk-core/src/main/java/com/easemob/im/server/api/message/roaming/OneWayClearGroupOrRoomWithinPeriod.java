package com.easemob.im.server.api.message.roaming;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

public class OneWayClearGroupOrRoomWithinPeriod {
    private final Context context;

    public OneWayClearGroupOrRoomWithinPeriod(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String username, String id, long time) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/rest/message/roaming/group/user/%s/time?groupId=%s&delTime=%d", username, id, time))
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
