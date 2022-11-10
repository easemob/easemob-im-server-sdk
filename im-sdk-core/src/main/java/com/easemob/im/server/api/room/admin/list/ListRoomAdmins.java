package com.easemob.im.server.api.room.admin.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, ListRoomAdminsResponse.class))
                .flatMapIterable(ListRoomAdminsResponse::getAdmins);
    }
}
