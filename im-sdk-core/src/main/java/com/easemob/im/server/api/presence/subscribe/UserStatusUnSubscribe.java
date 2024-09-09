package com.easemob.im.server.api.presence.subscribe;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserStatusUnSubscribe {

    private Context context;

    public UserStatusUnSubscribe(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String operator, List<String> usernames) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .delete()
                        .uri(String.format("/users/%s/presence", operator))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(usernames))))
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
                .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty())
                .then();
    }
}
