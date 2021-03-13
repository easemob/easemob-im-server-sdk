package com.easemob.im.server.api.room.member.remove;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class RemoveRoomMember {

    public static Mono<Void> single(Context context, String roomId, String username) {
        return context.getHttpClient()
                .delete()
                .uri(String.format("/chatrooms/%s/users/%s", roomId, username))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, RemoveRoomMemberResponse.class))
                .handle((rsp, sink) -> {
                    if (!rsp.isSuccess()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.complete();
                });
    }

}
