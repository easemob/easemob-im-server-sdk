package com.easemob.im.server.api.user.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMPage;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListUsers {

    private Context context;

    public ListUsers(Context context) {
        this.context = context;
    }

    public Flux<String> all(int limit) {
        return next(limit, null)
                .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(limit, rsp.getCursor()))
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
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, UserListResponse.class))
                .map(UserListResponse::toEMPage);
    }

}
