package com.easemob.im.server.api.contact;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.contact.user.ContactUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContactApi {

    private Context context;

    public ContactApi(Context context) {
        this.context = context;
    }

    /**
     * Add a contact to the user.
     *
     * @param user the user's username
     * @param contact the contact's username
     *
     * @return A {@code Mono} which complete on success.
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#add-contact">add-contact</a>
     */
    public Mono<Void> add(String user, String contact) {
        return ContactUser.add(this.context, user, contact);
    }

    /**
     * Remove a contact from the user.
     *
     * @param user the user's username
     * @param contact the contact's username
     *
     * @return A {@code Mono} which complete on success.
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#remove-contact">remove-contact</a>
     */
    public Mono<Void> remove(String user, String contact) {
        return ContactUser.remove(this.context, user, contact);
    }

    /**
     * List all contacts of current user.
     *
     * @param user the user's username
     * @return a {@code Flux} which emits contact usernames
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#list-contacts">list-contacts</a>
     */
    public Flux<String> list(String user) {
        return ContactUser.list(this.context, user);
    }

}
