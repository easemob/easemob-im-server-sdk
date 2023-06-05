package com.easemob.im.server.api.block.room.msg.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class BlockUserSendMsgToRoom {

    private Context context;

    public BlockUserSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String username, String roomId, Duration duration) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/mute", roomId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(BlockUserSendMsgToRoomRequest.of(username, duration)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec()
                        .decode(buf, BlockUserSendMsgToRoomResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.getSuccess(username)) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

    public Mono<Void> batch(List<String> usernames, String roomId, Duration duration) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/mute", roomId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(BlockUserSendMsgToRoomRequest.of(usernames, duration)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec()
                        .decode(buf, BlockUserSendMsgToRoomResponse.class))
                .then();
    }
}
