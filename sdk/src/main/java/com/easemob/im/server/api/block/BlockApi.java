package com.easemob.im.server.api.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.block.group.join.BlockUserJoinGroup;
import com.easemob.im.server.api.block.group.msg.BlockUserSendMsgToGroup;
import com.easemob.im.server.api.block.login.BlockUserLogin;
import com.easemob.im.server.api.block.room.msg.block.BlockUserSendMsgToRoom;
import com.easemob.im.server.api.block.room.msg.block.BlockUserSendMsgToRoomResponse;
import com.easemob.im.server.api.block.room.msg.list.ListUsersBlockedSendMsgToRoom;
import com.easemob.im.server.api.block.room.msg.unblock.UnblockUserSendMsgToRoom;
import com.easemob.im.server.api.block.user.SendMsgToUser;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
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
        return BlockUserLogin.blockUser(this.context, username);
    }

    /**
     * Unblock a user from login.
     *
     * @param username the user's username
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> unblockUserLogin(String username) {
        return BlockUserLogin.unblockUser(this.context, username);
    }

    /**
     * Get users blocked to join specified group.
     *
     * @param groupId the group's id
     * @return A {@code Flux} which emits {@code EMBlock}s.
     */
    public Flux<EMBlock> getUsersBlockedJoinGroup(String groupId) {
        return BlockUserJoinGroup.getBlockedUsers(this.context, groupId);
    }

    /**
     * Block a user from joining specified group.
     *
     * @param username the user's username
     * @param groupId the group's id
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> blockUserJoinGroup(String username, String groupId) {
        return BlockUserJoinGroup.blockUser(this.context, username, groupId);
    }

    /**
     * Unblock a user from joining specified group.
     *
     * @param username the user's username
     * @param groupId the group's id
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> unblockUserJoinGroup(String username, String groupId) {
        return BlockUserJoinGroup.unblockUser(this.context, username, groupId);
    }

    /**
     * Get users blocked to send message to specified group.
     *
     * @param groupId the group's id
     * @return A {@code Flux} which emits {@code EMBlock}s.
     */
    public Flux<EMBlock> getUsersBlockedSendMsgToGroup(String groupId) {
        return BlockUserSendMsgToGroup.getBlockedUsers(this.context, groupId);
    }

    /**
     * Block a user from sending message to specified group for a period.
     *
     * @param username the user's username
     * @param groupId the group's id
     * @param duration the block's period
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> blockUserSendMsgToGroup(String username, String groupId, Duration duration) {
        return BlockUserSendMsgToGroup.blockUser(this.context, username, groupId, duration);
    }

    /**
     * Unblock a user from sending message to specified group.
     *
     * @param username the user's username
     * @param groupId the group's id
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> unblockUserSendMsgToGroup(String username, String groupId) {
        return BlockUserSendMsgToGroup.unblockUser(this.context, username, groupId);
    }

    /**
     * List users blocked to send message in the room.
     *
     * @param roomId the room's id
     * @return A {@code Flux} of blocked users.
     */
    public Flux<EMBlock> listUsersBlockedSendMsgToRoom(String roomId) {
        return ListUsersBlockedSendMsgToRoom.all(this.context, roomId);
    }

    /**
     * Block a user from send message in the room.
     *
     * @param username the user's username
     * @param roomId the room's id
     * @param duration the blocking duration
     * @return A {@code Mono} which completes upon success.
     */
    public Mono<Void> blockUserSendMsgToRoom(String username, String roomId, Duration duration) {
        return BlockUserSendMsgToRoom.single(this.context, username, roomId, duration);
    }

    /**
     * Unblock a user from send message in the room.
     *
     * @param username the user's username
     * @param roomId the room's id
     * @return A {@code Mono} which completes upon success.
     */
    public Mono<Void> unblockUserSendMsgToRoom(String username, String roomId) {
        return UnblockUserSendMsgToRoom.single(this.context, username, roomId);
    }
}
