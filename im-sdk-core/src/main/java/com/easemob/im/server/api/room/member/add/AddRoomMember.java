package com.easemob.im.server.api.room.member.add;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class AddRoomMember {
    
    private Context context;

    public AddRoomMember(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String roomId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/users/%s", roomId, username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, AddRoomMemberResponse.class))
                        .handle((rsp, sink) -> {
                            if (!rsp.isSuccess()) {
                                sink.error(new EMUnknownException("unknown"));
                                return;
                            }
                            sink.complete();
                        }));

    }

}
