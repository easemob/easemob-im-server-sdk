package com.easemob.im.server.api.block.room.msg.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import reactor.core.publisher.Mono;

public class BlockAllUserSendMsgToRoom {

    private Context context;

    public BlockAllUserSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/ban", roomId))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                )
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .then();
    }
}
