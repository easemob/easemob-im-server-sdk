package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.List;

public class BlockUser {

    private Context context;

    private List<String> blockingUsernames;

    public BlockUser(Context context, List<String> blockUsernames) {
        this.context = context;
        this.blockingUsernames = blockUsernames;
    }

    /**
     * Block a user from sending messages to another user.
     *
     * To block userA sending messages to userB:
     * <code>
     *      EMService service;
     *      service.block().user("userA").fromUser("userB");
     * </code>
     *
     * @param username username of the receiving user
     * @return A {@code Mono<Void>} complete if successful
     */
    public Mono<Void> fromSendingMessagesToUser(String username) {
        return this.context.getHttpClient()
            .post()
            .uri(String.format("/users/%s/blocks/users", username))
            .send(Mono.just(this.context.getCodec().encode(new BlockUserRequest(this.blockingUsernames))))
            .response()
            .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
            .then();
    }


}
