package com.easemob.im.server.api.chatgroups.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMGroupDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class GroupDetail {

    private Context context;

    private String groupId;

    public GroupDetail(Context context, String groupId) {
        this.context = context;
        this.groupId = groupId;
    }

    public Mono<EMGroupDetail> execute() {
        return this.context.getHttpClient()
                .get()
                .uri(String.format("/chatgroups/%s", this.groupId))
                .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, GroupDetailResponse.class))
                .map(rsp -> {
                    EMGroupDetail detail = rsp.toGroupDetail(this.groupId);
                    if (detail == null) {
                        throw new EMNotFoundException(String.format("group:%s", this.groupId));
                    }
                    return detail;
                });
    }
}
