package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContactUser {

    public static Mono<Void> add(Context context, String user, String contact) {
        return context.getHttpClient()
            .post()
            .uri(String.format("/users/%s/contacts/users/%s", user, contact))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }

    public static Mono<Void> remove(Context context, String user, String contact) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s/contacts/users/%s", user, contact))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }

    public static Flux<String> list(Context context, String user) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/contacts/users", user))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, ContactUserListResponse.class))
            .flatMapIterable(ContactUserListResponse::getUsernames);
    }

}
