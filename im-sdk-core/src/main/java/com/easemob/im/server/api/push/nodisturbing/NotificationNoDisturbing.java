package com.easemob.im.server.api.push.nodisturbing;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.push.nodisturbing.close.CloseNotificationNoDisturbingRequest;
import com.easemob.im.server.api.push.nodisturbing.open.OpenNotificationNoDisturbingRequest;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class NotificationNoDisturbing {
    private Context context;

    public NotificationNoDisturbing(Context context) {
        this.context = context;
    }

    public Mono<Void> open(String username, String startTime, String endTime) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new OpenNotificationNoDisturbingRequest(startTime, endTime)))))
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
                .then();
    }

    public Mono<Void> close(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CloseNotificationNoDisturbingRequest()))))
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
                .then();
    }
}
