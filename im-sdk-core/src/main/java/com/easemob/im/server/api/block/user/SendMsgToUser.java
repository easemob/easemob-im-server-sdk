package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class SendMsgToUser {
    private Context context;

    public SendMsgToUser(Context context) {
        this.context = context;
    }

    public Flux<EMBlock> getUsersBlocked(String username) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/blocks/users", username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec()
                        .decode(buf, GetUsersBlockedSendMsgToUserResponse.class))
                .flatMapIterable(GetUsersBlockedSendMsgToUserResponse::getUsernames)
                .map(blockedUsername -> new EMBlock(blockedUsername, null));
    }

    public Mono<Void> blockUser(String fromUser, String toUser) {
        if (fromUser.equals(toUser)) {
            return Mono.error(new EMInvalidArgumentException("user could not block himself"));
        }
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/users/%s/blocks/users", toUser))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new BlockUsersSendMsgToUserRequest(
                                        Arrays.asList(fromUser))))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }

    public Mono<Void> unblockUser(String fromUser, String toUser) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/users/%s/blocks/users/%s", toUser, fromUser))
                        .responseSingle((rsp, buf) -> {
                            this.context.getErrorMapper().statusCode(rsp);
                            return buf;
                        })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }

}
