package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContactUser {

    private Context context;

    public ContactUser(Context context) {
        this.context = context;
    }

    public Mono<Void> add(String user, String contact) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/users/%s/contacts/users/%s", user, contact))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then()));
    }

    public Mono<Void> remove(String user, String contact) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/users/%s/contacts/users/%s", user, contact))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then()));
    }

    public Flux<String> list(String user) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/contacts/users", user))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, ContactUserListResponse.class))
                        .flatMapIterable(ContactUserListResponse::getUsernames));
    }

}
