package com.easemob.im.server.api.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.block.user.Login;
import com.easemob.im.server.api.block.user.SendMsgToUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BlockApi {

    private Context context;

    public BlockApi(Context context) {
        this.context = context;
    }

    /**
     * Get user(s) blocked from sending messages to this user.
     *
     * @param username the user's username
     * @return A {@code Flux} which emits each blocked username.
     */
    public Flux<String> getUsersBlockedFromSendMsgToUser(String username) {
        return SendMsgToUser.getUsersBlocked(this.context, username);
    }

    /**
     * Block user(s) from sending messages to this user.
     *
     * To block userA and userB from sending messages to userC:
     * <code>
     *      EMService service;
     *      service.block().blockUserFromSendingMessagesToUser(Flux.just("userA", "userB"), "userC");
     * </code>
     *
     * Note that user could NOT block himself.
     *
     * @param blockUsers the blocking user's username list
     * @param toUser the receiving user's username
     * @return A {@code Flux} which emits usernames successfully blocked.
     *
     */
    public Mono<Void> blockUsersSendMsgToUser(List<String> blockUsers, String toUser) {
        return SendMsgToUser.blockUsers(this.context, blockUsers, toUser);
    }

    /**
     * Unblock users from sending message to this user.
     *
     * @param unblockUsers the unblocking users's username list
     * @param toUser the receiving user's username
     * @return A {@code Publisher} of usernames successfully un-blocked.
     */
    public Mono<Void> unblockUsersSendMsgToUser(List<String> unblockUsers, String toUser) {
        return SendMsgToUser.unblockUsers(this.context, unblockUsers, toUser);
    }

    /**
     * Block a user from login.
     *
     * @return A {@code Flux<String>} emit usernames successfully blocked.
     */
    public Mono<Void> blockUserLogin(String username) {
        return Login.blockUser(this.context, username);
    }

    /**
     * Unblock a user from login.
     *
     * @param username the user's username
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> unblockUserLogin(String username) {
        return Login.unblockUser(this.context, username);
    }
}
