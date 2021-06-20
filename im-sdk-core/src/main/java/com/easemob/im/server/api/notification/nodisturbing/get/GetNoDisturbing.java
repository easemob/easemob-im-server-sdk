package com.easemob.im.server.api.notification.nodisturbing.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMNotificationUserSetting;
import reactor.core.publisher.Mono;

public class GetNoDisturbing {
    private Context context;

    public GetNoDisturbing(Context context) {
        this.context = context;
    }

    public Mono<EMNotificationUserSetting> getNoDisturbing(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, GetNoDisturbingResponse.class))
                .handle((rsp, sink) -> {
                    EMNotificationUserSetting noDisturbing = rsp.toNotificationNoDisturbing();
                    if (noDisturbing == null) {
                        sink.error(new EMUnknownException(String.format("user:%s", username)));
                        return;
                    }
                    sink.next(noDisturbing);
                });
    }
}
