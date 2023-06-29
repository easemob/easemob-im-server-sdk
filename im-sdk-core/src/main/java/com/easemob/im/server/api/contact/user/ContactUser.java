package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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

    public Mono<Void> remove(String user, String contact) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/users/%s/contacts/users/%s", user, contact))
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

    public Flux<String> list(String user) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/contacts/users", user))
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
                .map(buf -> this.context.getCodec()
                        .decode(buf, ContactUserListResponse.class))
                .flatMapIterable(ContactUserListResponse::getUsernames);
    }

}
