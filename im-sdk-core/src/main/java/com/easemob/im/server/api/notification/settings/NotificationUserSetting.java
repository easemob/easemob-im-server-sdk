package com.easemob.im.server.api.notification.settings;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMNotificationUserSetting;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class NotificationUserSetting {

    public static Mono<Void> update(Context context, String username, Consumer<NotificationUserSettingUpdateRequest> updater) {
        NotificationUserSettingUpdateRequest request = new NotificationUserSettingUpdateRequest();
        updater.accept(request);
        if (request.isEmpty()) {
            return Mono.error(new EMInvalidArgumentException("nothing to update"));
        }
        return context.getHttpClient()
            .put()
            .uri(String.format("/users/%s", username))
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(request))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }

    public static Mono<EMNotificationUserSetting> get(Context context, String username) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/users/%s", username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, NotificationUserSettingGetResponse.class))
                .handle((rsp, sink) -> {
                    EMNotificationUserSetting settings = rsp.getEMNotificationSetting(username);
                    if (settings == null) {
                        sink.complete();
                    } else {
                        sink.next(settings);
                    }
                });
    }
}
