package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class SendMsgToUser {


    public static Flux<String> getUsersBlocked(Context context, String username) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/users/%s/blocks/users", username))
                .responseSingle((httpRsp, buf) -> context.getErrorMapper().apply(httpRsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, GetUsersBlockedSendMsgToUserResponse.class))
                .flatMapIterable(GetUsersBlockedSendMsgToUserResponse::getUsernames);
    }

    public static Mono<Void> blockUsers(Context context, List<String> blockUsers, String toUser) {
        List<String> blockUsersExceptSelf = blockUsers.stream()
                .filter(user -> !user.equals(toUser))
                .collect(Collectors.toList());
        return context.getHttpClient()
                .post()
                .uri(String.format("/users/%s/blocks/users", toUser))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(new BlockUsersSendMsgToUserRequest(blockUsersExceptSelf)))))
                .response()
                .flatMap(rsp -> context.getErrorMapper().apply(rsp).then());
    }

    public static Mono<Void> unblockUsers(Context context, List<String> unblockUsers, String toUser) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s/blocks/users", toUser))
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new UnblockUsersSendMsgToUserRequest(unblockUsers)))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }



}
