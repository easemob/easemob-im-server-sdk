package com.easemob.im.server.api.group.admin;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GroupAdminList {

    private Context context;

    public GroupAdminList(Context context) {
        this.context = context;
    }

    public Flux<String> all(String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s/admin", groupId))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, GroupAdminListResponse.class))
                .flatMapIterable(GroupAdminListResponse::getAdmins);
    }

}
