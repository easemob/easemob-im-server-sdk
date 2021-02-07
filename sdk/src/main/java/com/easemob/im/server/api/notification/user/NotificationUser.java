package com.easemob.im.server.api.notification.user;

import com.easemob.im.server.api.Context;
import com.fasterxml.jackson.annotation.JsonValue;
import reactor.core.publisher.Mono;

// TODO: make NotificationUser... static
public class NotificationUser {
    private Context context;

    public NotificationUser(Context context) {
        this.context = context;
    }

    public Mono<Void> setNickName(String username, String nickname) {
        return this.context.getHttpClient()
            .put()
            .uri(String.format("/users/%s", username))
            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new NotificationUserSetNicknameRequest(nickname)))))
            .response()
            .flatMap(this.context.getErrorMapper()::apply)
            .then();
    }

    public enum Style {
        DEFAULT(0),
        MESSAGE_CONTENT(1);

        int value;

        Style(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }
    }

    public Mono<Void> setStyle(String username, Style style) {
        return this.context.getHttpClient()
            .put()
            .uri(String.format("/users/%s", username))
            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new NotificationUserSetStyleRequest(style)))))
            .response()
            .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
            .then();
    }
}
