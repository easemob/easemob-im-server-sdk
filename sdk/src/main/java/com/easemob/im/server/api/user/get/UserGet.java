package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.UserResource;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserGet {

    private Context context;

    public UserGet(Context context) {
        this.context = context;
    }

    public Mono<EMUser> single(String username) {
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users/%s", username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, UserGetResponse.class))
            .map(response -> {
                if (response.getEntities().isEmpty()) {
                    throw new EMUnknownException("get single user returns empty");
                }
                UserResource user = response.getEntities().get(0);
                return new EMUser(user.getUsername(), !user.isActivated());
            });
    }

    /**
     * 从头开始遍历全部用户。
     *
     * @param limit 一次从服务器取多少用户
     * @return {@code Flux<EMUser>}
     */
    public Flux<EMUser> all(int limit) {
        return all(limit, null)
            .expand(rsp -> {
                if (rsp.getCursor() == null || rsp.getCursor().isEmpty()) {
                    return Mono.empty();
                }
                return all(limit, rsp.getCursor());
            })
            .limitRate(1)
            .concatMapIterable(UserGetResponse::getEntities)
            .map(UserResource::toUser)
            .limitRate(limit);
    }

    /** 从cursor开始遍历全部用户。
     *
     * @param limit 一次从服务器取回多少用户
     * @param cursor 从哪里开始，首次可以传{@code Optional.empty()}，或者从上次返回的
     * @return {@code Mono<UserGetResponse>}
     */
    public Mono<UserGetResponse> all(int limit, String cursor) {
        String query = String.format("limit=%d", limit);
        if (cursor != null) {
            query = String.format("%s&cursor=%s", query, cursor);
        }
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users?%s", query))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, UserGetResponse.class));
    }

}
