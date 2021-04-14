package com.easemob.im.server.api.block.room.msg.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;

public class ListUsersBlockedSendMsgToRoom {

    private Context context;

    public ListUsersBlockedSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Flux<EMBlock> all(String roomId) {
        return this.context.getHttpClient()
                .flatMapMany(HttpClient -> HttpClient.get()
                        .uri(String.format("/chatrooms/%s/mute", roomId))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, ListUsersBlockedSendMsgToRoomResponse.class))
                        .flatMapIterable(ListUsersBlockedSendMsgToRoomResponse::getEMBlocks));
    }

}
