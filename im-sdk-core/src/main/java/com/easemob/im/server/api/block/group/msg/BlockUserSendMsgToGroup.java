package com.easemob.im.server.api.block.group.msg;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.function.Function;

public class BlockUserSendMsgToGroup {
    
    private Context context;

    public BlockUserSendMsgToGroup(Context context) {
        this.context = context;
    }

    public Flux<EMBlock> getBlockedUsers(String groupId) {
        return this.context.getHttpClient()
                .flatMapMany(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s/mute", groupId))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(rsp -> this.context.getCodec().decode(rsp, GetUsersBlockedSendMsgToGroupResponse.class))
                        .flatMapIterable(GetUsersBlockedSendMsgToGroupResponse::getEMBlocks));

    }
    public Mono<Void> blockUser(String username, String groupId, Duration duration) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatgroups/%s/mute", groupId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(BlockUserSendMsgToGroupRequest.of(username, duration)))))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, BlockUserSendMsgToGroupResponse.class))
                        .handle((rsp, sink) -> {
                            if (!rsp.getSuccess(username)) {
                                sink.error(new EMUnknownException("unknown"));
                                return;
                            }
                            sink.complete();
                        }));
    }

    public Mono<Void> unblockUser(String username, String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                            .uri(String.format("/chatgroups/%s/mute/%s", groupId, username))
                            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                            .map(buf -> this.context.getCodec().decode(buf, UnblockUserSendMsgToGroupResponse.class))
                            .handle((rsp, sink) -> {
                                if (!rsp.getSuccess(username)) {
                                    sink.error(new EMUnknownException("unknown"));
                                    return;
                                }
                                sink.complete();
                            }));
    }
}
