package com.easemob.im.server.api.chatrooms.create;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.List;

public class CreateRoom {


    public static Mono<String> createRoom(Context context, String name, String description, String owner, List<String> members, int maxMembers) {
        return context.getHttpClient()
                .post()
                .uri("/chatrooms")
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(CreateRoomRequest.of(name, description, owner, members, maxMembers)))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, CreateRoomResponse.class).getRoomId());
    }


}
