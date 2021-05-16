package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMRoom;
import reactor.core.publisher.Mono;

public class GetRoomDetail {
    
    private Context context;

    public GetRoomDetail(Context context) {
        this.context = context;
    }

    public Mono<EMRoom> byId(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatrooms/%s", roomId))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, GetRoomDetailResponse.class).toRoomDetails().get(0));
    }

}
