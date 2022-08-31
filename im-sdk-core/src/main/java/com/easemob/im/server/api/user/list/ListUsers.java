package com.easemob.im.server.api.user.list;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMPage;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public class ListUsers {

    private Context context;

    public ListUsers(Context context) {
        this.context = context;
    }

    public Flux<String> all(int limit) {
        return next(limit, null)
                .expand(rsp -> rsp.getCursor() == null ?
                        Mono.empty() :
                        next(limit, rsp.getCursor()))
                .limitRate(1)
                .concatMapIterable(EMPage::getValues)
                .limitRate(limit);
    }

    public Mono<EMPage<String>> next(int limit, String cursor) {
        QueryStringEncoder encoder = new QueryStringEncoder("/users");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        final String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                        .flatMap(tuple2 -> {
                            HttpClientResponse clientResponse = tuple2.getT1();

                            return Mono.defer(() -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(clientResponse);
                                mapper.checkError(tuple2.getT2());
                                return Mono.just(tuple2.getT2());
                            }).onErrorResume(e -> {
                                if (e instanceof EMException) {
                                    return Mono.error(e);
                                }
                                return Mono.error(new EMUnknownException(e.getMessage()));
                            }).flatMap(byteBuf -> {
                                UserListResponse userListResponse = this.context.getCodec().decode(byteBuf, UserListResponse.class);
                                return Mono.just(userListResponse.toEMPage());
                            });
                        }));
    }

}
