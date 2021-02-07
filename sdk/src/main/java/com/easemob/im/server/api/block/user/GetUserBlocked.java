package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;

// TODO: make GetUserBlocked... static
public class GetUserBlocked {
    private Context context;

    public GetUserBlocked(Context context) {
        this.context = context;
    }

    /**
     * Get who is blocked to send messages to user.
     *
     * @param username username of the receiving user
     * @return {@code Flux<String>} which emits blocked usernames
     */
    public Flux<String> fromSendingMessagesTo(String username) {
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/blocks/users", username))
            .responseSingle((httpRsp, buf) -> this.context.getErrorMapper().apply(httpRsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GetUserBlockedResponse.class))
            .flatMapIterable(GetUserBlockedResponse::getUsernames);
    }

}
