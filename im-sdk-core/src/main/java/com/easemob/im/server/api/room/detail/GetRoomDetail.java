package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMRoom;
import reactor.core.publisher.Mono;

public class GetRoomDetail {

    public static Mono<EMRoom> byId(Context context, String roomId) {
        return context.getHttpClient()
                .get()
                .uri(String.format("/chatrooms/%s", roomId))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, GetRoomDetailResponse.class).toRoomDetails().get(0));
    }


}
