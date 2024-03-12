package com.easemob.im.server.api.block.room.msg.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListUsersBlockedSendMsgToRoom {

    private Context context;

    public ListUsersBlockedSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Flux<EMBlock> all(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatrooms/%s/mute", roomId))
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
                    ListUsersBlockedSendMsgToRoomResponse response = this.context.getCodec()
                            .decode(buf, ListUsersBlockedSendMsgToRoomResponse.class);
                    buf.release();
                    return response;
                })
                .flatMapIterable(ListUsersBlockedSendMsgToRoomResponse::getEMBlocks);
    }

}
