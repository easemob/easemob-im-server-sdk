package com.easemob.im.server.api.chatgroups.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupDetail;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class GroupDetail {

    private Context context;

    public GroupDetail(Context context) {
        this.context = context;
    }

    public Flux<EMGroupDetail> byId(Flux<String> groupIds) {
        return groupIds.window(10)
            .flatMap(g -> g.collect(Collectors.joining(",")))
            .flatMap(groupIdList -> this.context.getHttpClient()
                .get()
                .uri(String.format("/chatgroups/%s", groupIdList))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, GroupDetailResponse.class))
                .flatMapIterable(GroupDetailResponse::toGroupDetails));
    }
}
