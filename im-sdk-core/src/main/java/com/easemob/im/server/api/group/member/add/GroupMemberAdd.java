package com.easemob.im.server.api.group.member.add;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.create.CreateUserRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public class GroupMemberAdd {

    private Context context;

    public GroupMemberAdd(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String groupId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatgroups/%s/users/%s", groupId, username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }

    public Mono<Void> batch(String groupId, List<String> usernames) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatgroups/%s/users", groupId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new GroupMemberAddRequest(usernames)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }

}
