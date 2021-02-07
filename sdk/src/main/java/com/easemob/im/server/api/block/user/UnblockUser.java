package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.Context;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class UnblockUser {
    private Context context;

    private Publisher<String> unblockUsernames;

    public UnblockUser(Context context, Publisher<String> unblockUsernames) {
        this.context = context;
        this.unblockUsernames = unblockUsernames;
    }

    /**
     * Unblock users from sending message to this user.
     *
     * @param username the receiving user's username
     * @return A {@code Publisher} of usernames successfully un-blocked.
     */
    public Publisher<String> fromSendMessageToUser(String username) {
        return Flux.from(this.unblockUsernames)
            .window(10)
            .flatMap(usernameFlux -> usernameFlux.collectList()
                .flatMapMany(usernames -> this.context.getHttpClient()
                    .delete()
                    .uri(String.format("/users/%s/blocks/users", username))
                    .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new UnblockUserRequest(usernames)))))
                    .response()
                    .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
                    .thenMany(Flux.fromIterable(usernames))));
    }

    /**
     * Unblock user(s) from login.
     *
     * @return A {@code Flux<String>} emit usernames successfully blocked.
     */
    public Publisher<String> fromLogin() {
        return Flux.from(this.unblockUsernames)
            .flatMap(username -> this.context.getHttpClient()
                .post()
                .uri(String.format("/users/%s/activate", username))
                .response()
                .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
                .thenReturn(username));
    }
}
