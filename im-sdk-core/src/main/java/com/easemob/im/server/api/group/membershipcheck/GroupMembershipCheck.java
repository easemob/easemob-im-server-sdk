package com.easemob.im.server.api.group.membershipcheck;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class GroupMembershipCheck {

    private Context context;

    public GroupMembershipCheck(Context context) {
        this.context = context;
    }

    public Mono<Boolean> execute(String groupId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s/user/%s/is_joined", groupId, username))
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
                .map(buf -> this.context.getCodec().decode(buf, GroupMembershipCheckResponse.class))
                .map(GroupMembershipCheckResponse::getData);
    }
}
