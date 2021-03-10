package com.easemob.im.server.api.block.room.msg.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;

public class ListUsersBlockedSendMsgToRoom {

    public static Flux<EMBlock> all(Context context, String roomId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatrooms/%s/mute", roomId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, ListUsersBlockedSendMsgToRoomResponse.class))
                .flatMapIterable(ListUsersBlockedSendMsgToRoomResponse::getEMBlocks);
    }

}
