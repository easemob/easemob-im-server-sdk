package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SendMsgToUser {


    public static Flux<EMBlock> getUsersBlocked(Context context, String username) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/users/%s/blocks/users", username))
                .responseSingle((httpRsp, buf) -> context.getErrorMapper().apply(httpRsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, GetUsersBlockedSendMsgToUserResponse.class))
                .flatMapIterable(GetUsersBlockedSendMsgToUserResponse::getUsernames)
                .map(blockedUsername -> new EMBlock(blockedUsername, null));
    }

    public static Mono<Void> blockUser(Context context, String fromUser, String toUser) {
        if (fromUser.equals(toUser)) {
            return Mono.error(new EMInvalidArgumentException("user could not block himself"));
        }
        return context.getHttpClient()
                .post()
                .uri(String.format("/users/%s/blocks/users", toUser))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(new BlockUsersSendMsgToUserRequest(Arrays.asList(fromUser))))))
                .response()
                .flatMap(rsp -> context.getErrorMapper().apply(rsp).then());
    }

    public static Mono<Void> unblockUser(Context context, String fromUser, String toUser) {
        return context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s/blocks/users", toUser))
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new UnblockUsersSendMsgToUserRequest(Arrays.asList(fromUser))))))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then());
    }



}
