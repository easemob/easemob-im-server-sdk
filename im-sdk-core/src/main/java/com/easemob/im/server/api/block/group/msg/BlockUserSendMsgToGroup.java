package com.easemob.im.server.api.block.group.msg;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BlockUserSendMsgToGroup {

    public static Flux<EMBlock> getBlockedUsers(Context context, String groupId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatgroups/%s/mute", groupId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(rsp -> context.getCodec().decode(rsp, GetUsersBlockedSendMsgToGroupResponse.class))
                .flatMapIterable(GetUsersBlockedSendMsgToGroupResponse::getEMBlocks);
    }

    public static Mono<Void> blockUser(Context context, String username, String groupId) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/chatgroups/%s/mute", groupId))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(BlockUserSendMsgToGroupRequest.of(username)))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, BlockUserSendMsgToGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public static Mono<Void> blockUser(Context context, String username, String groupId, Duration duration) {
        return context.getHttpClient()
                .post()
                .uri(String.format("/chatgroups/%s/mute", groupId))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(BlockUserSendMsgToGroupRequest.of(username, duration)))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, BlockUserSendMsgToGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public static Mono<Void> unblockUser(Context context, String username, String groupId) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatgroups/%s/mute/%s", groupId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, UnblockUserSendMsgToGroupResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }
}
