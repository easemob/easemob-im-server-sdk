package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.contact.ContactUserPageResource;
import com.easemob.im.server.api.user.create.CreateUserRequest;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .doOnSuccess(ReferenceCounted::release)
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
                .doOnSuccess(ReferenceCounted::release)
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
                .map(buf -> {
                    ContactUserListResponse response = this.context.getCodec()
                            .decode(buf, ContactUserListResponse.class);
                    buf.release();
                    return response;
                })
                .flatMapIterable(ContactUserListResponse::getUsernames);
    }

    public Mono<List<ContactUserPageResource>> list(String user, int limit, String cursor) {
        QueryStringEncoder encoder = new QueryStringEncoder(String.format("/user/%s/contacts", user));
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
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
                .map(buf -> {
                    ContactUserPageListResponse response = this.context.getCodec()
                            .decode(buf, ContactUserPageListResponse.class);
                    buf.release();
                    return response.getData().getContacts();
                });
    }

    public Mono<Void> setRemark(String user, String contact, String remark) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/user/%s/contacts/users/%s", user, contact))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ContactUserRemarkRequest(remark)))))
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
