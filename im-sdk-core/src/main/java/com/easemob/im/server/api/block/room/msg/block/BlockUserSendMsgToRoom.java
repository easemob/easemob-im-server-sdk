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
                    BlockUserSendMsgToRoomResponse response = this.context.getCodec()
                            .decode(buf, BlockUserSendMsgToRoomResponse.class);
                    buf.release();
                    return response;
                })
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
                    BlockUserSendMsgToRoomResponse response = this.context.getCodec()
                            .decode(buf, BlockUserSendMsgToRoomResponse.class);
                    buf.release();
                    return response;
                })
                .then();
    }
}
