package com.easemob.im.server.api.room.admin.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListRoomAdmins {

    private Context context;

    public ListRoomAdmins(Context context) {
        this.context = context;
    }

    public Flux<String> all(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatrooms/%s/admin", roomId))
                        .responseSingle((rsp, buf) -> {
                            return buf.switchIfEmpty(
                                            Mono.error(new EMUnknownException("response is null")))
                                    .flatMap(byteBuf -> {
                                        ErrorMapper mapper = new DefaultErrorMapper();
                                        mapper.statusCode(rsp);
                                        mapper.checkError(byteBuf);
                                        return Mono.just(byteBuf);
                                    });
                        }))
                .map(buf -> {
                    ListRoomAdminsResponse response =
                            this.context.getCodec().decode(buf, ListRoomAdminsResponse.class);
                    buf.release();
                    return response;
                })
                .flatMapIterable(ListRoomAdminsResponse::getAdmins);
    }
}
