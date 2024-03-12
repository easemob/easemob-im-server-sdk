package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import io.netty.util.ReferenceCounted;
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
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/blocks/users", username))
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
                    GetUsersBlockedSendMsgToUserResponse response = this.context.getCodec()
                            .decode(buf, GetUsersBlockedSendMsgToUserResponse.class);
                    buf.release();
                    return response;
                })
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

    public Mono<Void> unblockUser(String fromUser, String toUser) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/users/%s/blocks/users/%s", toUser, fromUser))
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
