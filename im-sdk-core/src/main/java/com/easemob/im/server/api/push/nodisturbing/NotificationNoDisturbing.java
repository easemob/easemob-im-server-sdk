package com.easemob.im.server.api.push.nodisturbing;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.push.nodisturbing.close.CloseNotificationNoDisturbingRequest;
import com.easemob.im.server.api.push.nodisturbing.open.OpenNotificationNoDisturbingRequest;
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
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }

    public Mono<Void> close(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new CloseNotificationNoDisturbingRequest()))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }
}
