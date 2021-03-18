package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeleteUser {

    private static final Logger log = LoggerFactory.getLogger(DeleteUser.class);

    private Context context;

    public DeleteUser(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username) {
        return this.context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s", username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(b -> this.context.getCodec().decode(b, UserUnregisterResponse.class))
            .handle((rsp, sink) -> {
                if (rsp.getError() != null) {
                    sink.error(new EMUnknownException(rsp.getError()));
                    return;
                }
                sink.complete();
            });
    }


    public Flux<String> all(int limit) {
        return next(limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : next(limit, rsp.getCursor()))
            .concatMapIterable(UserUnregisterResponse::getUsernames);
    }

    public Mono<UserUnregisterResponse> next(int limit, String cursor) {
        String query = String.format("limit=%d", limit);
        if (cursor != null) {
            query = String.format("%s&cursor=%s", query, cursor);
        }
        return this.context.getHttpClient().delete()
            .uri(String.format("/users?%s", query))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(b -> this.context.getCodec().decode(b, UserUnregisterResponse.class));
    }
}
