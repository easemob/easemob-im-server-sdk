package com.easemob.im.server.api.presence.set;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.presence.PresenceUserStatusResource;
import com.easemob.im.server.api.presence.get.PresenceUserStatusGetRequest;
import com.easemob.im.server.api.presence.get.PresenceUserStatusGetResponse;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserStatusSet {

    private Context context;

    public UserStatusSet(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String username, String resource, String status, String ext) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri(String.format("/users/%s/presence/%s/%s", username, resource, status))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new PresenceUserStatusSetRequest(ext)))))
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
