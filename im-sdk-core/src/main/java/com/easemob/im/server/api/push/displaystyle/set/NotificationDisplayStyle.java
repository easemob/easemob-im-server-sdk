package com.easemob.im.server.api.push.displaystyle.set;

import com.easemob.im.server.api.Context;

import reactor.core.publisher.Mono;

public class NotificationDisplayStyle {
    private Context context;

    public NotificationDisplayStyle(Context context) {
        this.context = context;
    }

    public Mono<Void> set(String username, String style) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s", username))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new NotificationDisplayStyleRequest(style)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }
}
