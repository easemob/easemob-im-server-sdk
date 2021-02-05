package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserUnregister {

    private static final Logger log = LogManager.getLogger();

    public static Mono<EMUser> single(Context context, String username) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s", username))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(b -> context.getCodec().decode(b, UserUnregisterResponse.class))
            .handle((rsp, sink) -> {
                EMUser user = rsp.getEMUser(username);
                if (user != null) {
                    sink.next(user);
                }
            });
    }


    public static Flux<EMUser> all(Context context, int limit) {
        return all(context, limit, null)
            .expand(rsp -> rsp.getCursor() == null ? Mono.empty() : all(context, limit, rsp.getCursor()))
            .concatMapIterable(UserUnregisterResponse::getEMUsers);
    }

    public static Mono<UserUnregisterResponse> all(Context context, int limit, String cursor) {
        String query = String.format("limit=%d", limit);
        if (cursor != null) {
            query = String.format("%s&cursor=%s", query, cursor);
        }
        return context.getHttpClient().delete()
            .uri(String.format("/users?%s", query))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(b -> context.getCodec().decode(b, UserUnregisterResponse.class));
    }
}
