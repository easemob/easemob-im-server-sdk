package com.easemob.im.server.api.room.create;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.List;

public class CreateRoom {
    
    private Context context;

    public CreateRoom(Context context) {
        this.context = context;
    }

    public Mono<String> createRoom(String name, String description, String owner, List<String> members, int maxMembers) {
        return this.context.getHttpClient()
                .post()
                .uri("/chatrooms")
                .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(CreateRoomRequest.of(name, description, owner, members, maxMembers)))))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, CreateRoomResponse.class).getRoomId());
    }


}
