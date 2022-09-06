package com.easemob.im.server.api.block.room.msg.block;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public class BlockAllUserSendMsgToRoom {

    private Context context;

    public BlockAllUserSendMsgToRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/ban", roomId))
                        .responseSingle((rsp, buf) -> {
                            return Mono.zip(Mono.just(rsp), buf);
                        })
                        .flatMap(tuple2 -> {
                            HttpClientResponse clientResponse = tuple2.getT1();

                            return Mono.defer(() -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(clientResponse);
                                mapper.checkError(tuple2.getT2());
                                return Mono.just(tuple2.getT2());
                            }).onErrorResume(e -> {
                                if (e instanceof EMException) {
                                    return Mono.error(e);
                                }
                                return Mono.error(new EMUnknownException(e.getMessage()));
                            });
                        }))
                .then();
    }
}
