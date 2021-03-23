package com.easemob.im.server.api.room.superadmin.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class ListRoomSuperAdmins {

    public static Mono<ListRoomSuperAdminsResponse> next(Context context, int pagesize, int pagenum) {
        String uri = String.format("/chatrooms/super_admin?pagenum=%d&pagesize=%d", pagenum, pagesize);

        return context.getHttpClient()
                .get()
                .uri(uri)
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, ListRoomSuperAdminsResponse.class));
    }

    public static Flux<String> all(Context context, int pagesize) {
        return Flux.<List<String>, Integer> generate(() -> 1, (pagenum, sink) -> {
            List<String> admins = next(context, pagesize, pagenum).doOnError(sink::error).block().getAdmins();
            if (admins.isEmpty()) {
                sink.complete();
            } else {
                sink.next(admins);
            }
            return pagenum + 1;
        }).flatMap(Flux::fromIterable);
    }
}
