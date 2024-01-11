package com.easemob.im.server.api.group.management;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class GroupDisable {
    private Context context;

    public GroupDisable(Context context) {
        this.context = context;
    }

    public Mono<Boolean> execute(String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatgroups/%s/disable", groupId))
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
                .map(buf -> this.context.getCodec().decode(buf, GroupDisableResponse.class))
                .map(groupDisableResponse -> groupDisableResponse.getResource().getDisabled());
    }

}
