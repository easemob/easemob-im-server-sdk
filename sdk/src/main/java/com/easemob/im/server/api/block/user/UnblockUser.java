package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.List;

public class UnblockUser {
    private Context context;

    private List<String> unblockUsernames;

    public UnblockUser(Context context, List<String> unblockUsernames) {
        this.context = context;
        this.unblockUsernames = unblockUsernames;
    }

    public Mono<Void> fromSendMessagesToUser(String username) {
        return this.context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s/blocks/users", username))
            .send(Mono.just(this.context.getCodec().encode(new UnblockUserRequest(this.unblockUsernames))))
            .response()
            .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
            .then();
    }


}
