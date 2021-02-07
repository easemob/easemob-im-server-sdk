package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

//TODO: make BlockUser... static
public class BlockUser {

    private Context context;

    private Publisher<String> blockingUsernames;

    public BlockUser(Context context, Publisher<String> blockUsernames) {
        this.context = context;
        this.blockingUsernames = blockUsernames;
    }

    /**
     * Block user(s) from sending messages to this user.
     *
     * To block userA sending messages to userB:
     * <code>
     *      EMService service;
     *      service.block().user("userA").fromSendingMessagesToUser("userB");
     * </code>
     *
     * @param username the receiving user's username
     * @return A {@code Publisher} of usernames successfully blocked.
     */
    public Publisher<String> fromSendMessageToUser(String username) {
        return Flux.from(this.blockingUsernames)
            .window(10)
            .flatMap(usernameFlux -> usernameFlux.collectList()
                .flatMapMany(usernames -> this.context.getHttpClient()
                    .post()
                    .uri(String.format("/users/%s/blocks/users", username))
                    .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new BlockUserRequest(usernames)))))
                    .response()
                    .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
                    .thenMany(Flux.fromIterable(usernames))));

    }

    /**
     * Block user(s) from login.
     *
     * @return A {@code Flux<String>} emit usernames successfully blocked.
     */
    public Publisher<String> fromLogin() {
        return Flux.from(this.blockingUsernames)
            .flatMap(username -> this.context.getHttpClient()
                .post()
                .uri(String.format("/users/%s/deactivate", username))
                .response()
                .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
                .thenReturn(username));
    }

}
