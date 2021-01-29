package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContactUser {

    private Context context;

    private String username;

    public ContactUser(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    /**
     * Add a contact to current user.
     *
     * @param contactUsername username of the contact to add
     *
     * @return an {@code Mono<Void>} which emits error if any.
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#add-contact">add-contact</a>
     */
    public Mono<Void> add(String contactUsername) {
        return this.context.getHttpClient()
            .post()
            .uri(String.format("/users/%s/contacts/users/%s", this.username, contactUsername))
            .response()
            .flatMap(this.context.getErrorMapper()::apply)
            .then();
    }

    /**
     * Remove a contact from current user.
     *
     * @param contactUsername username of the contact to remove
     *
     * @return an {@code Mono<Void>} which emits error if any.
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#remove-contact">remove-contact</a>
     */
    public Mono<Void> remove(String contactUsername) {
        return this.context.getHttpClient()
            .delete()
            .uri(String.format("/users/%s/contacts/users/%s", this.username, contactUsername))
            .response()
            .flatMap(this.context.getErrorMapper()::apply)
            .then();
    }

    /**
     * List all contacts of current user.
     *
     * @return a {@code Flux<String>} which emits contact usernames
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#list-contacts">list-contacts</a>
     */
    public Flux<String> list() {
        return this.context.getHttpClient()
            .get()
            .uri(String.format("/users/%s/contacts/users", this.username))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, ContactUserListResponse.class))
            .log()
            .flatMapIterable(ContactUserListResponse::getUsernames);
    }

}
