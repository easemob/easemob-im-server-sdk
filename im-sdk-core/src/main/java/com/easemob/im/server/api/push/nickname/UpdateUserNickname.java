package com.easemob.im.server.api.push.nickname;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMPushNickname;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

import java.util.List;

public class UpdateUserNickname {

    private Context context;

    public UpdateUserNickname(Context context) {
        this.context = context;
    }

    public Mono<Void> update(String username, String nickname) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new UpdateUserNicknameRequest(nickname)))))
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

    public Mono<Void> update(List<EMPushNickname> pushNicknames) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri("/push/nickname")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(pushNicknames))))
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
