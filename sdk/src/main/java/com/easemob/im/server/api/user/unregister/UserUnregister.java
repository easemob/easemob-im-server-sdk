package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.BearerAuthorization;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.model.EMUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class UserUnregister {

    private static final Logger log = LogManager.getLogger();

    private final Context context;

    public UserUnregister(Context context) {
        this.context = context;
    }

    /**
     * 注销单个用户
     *
     * @param username 用户名
     * @return {@code Mono<EMUser>}
     */
    public Mono<EMUser> single(String username) {
        return this.context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s", username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(b -> this.context.getCodec().decode(b, UserUnregisterResponse.class))
            .flatMapIterable(UserUnregisterResponse::getEntities)
            .next()
            .map(UserResource::toUser);
    }

    /**
     * （自动翻页）注销全部用户。
     *
     * @param limit 每次删除多少个用户
     * @return {@code Mono<EMUser>}
     */
    public Flux<EMUser> all(int limit) {
        return all(limit, null)
            .expand(rsp -> {
                if (rsp.getCursor() == null) {
                    return Mono.empty();
                }
                return all(limit, rsp.getCursor());
            })
            .limitRate(1)
            .concatMapIterable(UserUnregisterResponse::getEntities)
            .map(UserResource::toUser)
            .limitRate(limit);
    }

    /**
     * （手动翻页）注销全部用户。
     *
     * @param limit how many user to delete each time
     * @param cursor from where to continue
     * @return {@code Mono<UserUnregisterResponse>}
     */
    public Mono<UserUnregisterResponse> all(int limit, String cursor) {
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
