package com.easemob.im.server.api.room.superadmin.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ListRoomSuperAdmins {

    private Context context;

    public ListRoomSuperAdmins(Context context) {
        this.context = context;
    }

    public Mono<ListRoomSuperAdminsResponse> next(int pagesize, int pagenum) {
        String uri =
                String.format("/chatrooms/super_admin?pagenum=%d&pagesize=%d", pagenum, pagesize);

        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uri)
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ListRoomSuperAdminsResponse.class));
    }

    public Flux<String> all(int pagesize) {
        return Flux.<List<String>, Integer>generate(() -> 1, (pagenum, sink) -> {
            List<String> admins =
                    next(pagesize, pagenum).doOnError(sink::error).block().getAdmins();
            if (admins.isEmpty()) {
                sink.complete();
            } else {
                sink.next(admins);
            }
            return pagenum + 1;
        }).flatMap(Flux::fromIterable);
    }
}
