package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUser;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class UserGet {

    private Context context;

    public UserGet(Context context) {
        this.context = context;
    }

    public Mono<EMUser> single(String username) {
        return this.context.getHttpClient()
            .headersWhen(this.context.getBearerAuthorization())
            .get()
            .uri(String.format("/users/%s", username))
            .responseSingle((rsp, buf) -> {
                if (!rsp.status().equals(HttpResponseStatus.OK)) {
                    return Mono.error(new EMUnknownException(rsp.toString()));
                }
                return buf.log();
            }).map(buf -> this.context.getCodec().decode(buf, UserGetResponse.class))
            .map(response -> {
                if (response.getEntities().isEmpty()) {
                    throw new EMUnknownException("get single user returns empty");
                }
                UserGetResponse.UserResource user = response.getEntities().get(0);
                return new EMUser(user.getUsername(), !user.isActivated());
            });
    }

    /**
     * 从头开始遍历全部用户。
     *
     * @param limit 一次从服务器取多少用户，最大200
     * @return
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
            .map(user -> new EMUser(user.getUsername(), !user.isActivated()))
            .limitRate(limit);
    }

    /** 从cursor开始遍历全部用户。
     *
     * @param limit 一次从服务器取回多少用户，最大200
     * @param cursor 从哪里开始，首次可以传{@code Optional.empty()}，或者从上次返回的
     * @return
     */
    public Mono<UserGetResponse> all(int limit, String cursor) {
        if (limit > 200) {
            throw new EMInvalidArgumentException("limit must not be greater than 200");
        } else if (limit < 2) {
            throw new EMInvalidArgumentException("limit must not be lesser than 2");
        }

        String query = String.format("limit=%d", limit);
        if (cursor != null) {
            query = String.format("%s&cursor=%s", query, cursor);
        }
        return this.context.getHttpClient()
            .headersWhen(this.context.getBearerAuthorization())
            .get()
            .uri(String.format("/users?%s", query))
            .responseSingle((rsp, buf) -> {
                // TODO: error mapper
                if (!rsp.status().equals(HttpResponseStatus.OK)) {
                    throw new EMUnknownException(rsp.toString());
                }
                return buf;
            }).map(buf -> this.context.getCodec().decode(buf, UserGetResponse.class));

    }

}
