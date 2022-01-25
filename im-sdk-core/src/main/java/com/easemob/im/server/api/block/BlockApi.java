package com.easemob.im.server.api.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.block.group.join.BlockUserJoinGroup;
import com.easemob.im.server.api.block.group.msg.BlockUserSendMsgToGroup;
import com.easemob.im.server.api.block.login.BlockUserLogin;
import com.easemob.im.server.api.block.room.join.BlockUserJoinRoom;
import com.easemob.im.server.api.block.room.msg.block.BlockUserSendMsgToRoom;
import com.easemob.im.server.api.block.room.msg.list.ListUsersBlockedSendMsgToRoom;
import com.easemob.im.server.api.block.room.msg.unblock.UnblockUserSendMsgToRoom;
import com.easemob.im.server.api.block.user.SendMsgToUser;
import com.easemob.im.server.model.EMBlock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 封禁API，提供封禁相关的功能。
 */
public class BlockApi {

    private SendMsgToUser sendMsgToUser;

    private BlockUserLogin blockUserLogin;

    private BlockUserJoinGroup blockUserJoinGroup;

    private BlockUserJoinRoom blockUserJoinRoom;

    private BlockUserSendMsgToGroup blockUserSendMsgToGroup;

    private UnblockUserSendMsgToRoom unblockUserSendMsgToRoom;

    private BlockUserSendMsgToRoom blockUserSendMsgToRoom;

    private ListUsersBlockedSendMsgToRoom listUsersBlockedSendMsgToRoom;

    public BlockApi(Context context) {
        this.sendMsgToUser = new SendMsgToUser(context);
        this.blockUserLogin = new BlockUserLogin(context);
        this.blockUserJoinGroup = new BlockUserJoinGroup(context);
        this.blockUserJoinRoom = new BlockUserJoinRoom(context);
        this.blockUserSendMsgToGroup = new BlockUserSendMsgToGroup(context);
        this.unblockUserSendMsgToRoom = new UnblockUserSendMsgToRoom(context);
        this.blockUserSendMsgToRoom = new BlockUserSendMsgToRoom(context);
        this.listUsersBlockedSendMsgToRoom = new ListUsersBlockedSendMsgToRoom(context);
    }

    /**
     * 获取禁言列表，即这个用户禁言的其他用户。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<EMBlock> blocks = service.block().getUsersBlockedFromSendMsgToUser("username").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 发起禁言的用户名
     * @return 每个被禁言用户的用户的用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E9%BB%91%E5%90%8D%E5%8D%95">获取用户禁言列表</a>
     */
    public Flux<EMBlock> getUsersBlockedFromSendMsgToUser(String username) {
        return this.sendMsgToUser.getUsersBlocked(username);
    }

    /**
     * 用户禁言，阻止向这个用户发消息。
     * <p>
     * 要阻止 userA 给 userB发送消息：
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().blockUserSendMsgToUser("userA", "userB").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param fromUser 被阻止的用户的用户名
     * @param toUser   接收消息的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%B7%BB%E5%8A%A0%E9%BB%91%E5%90%8D%E5%8D%95">添加用户禁言</a>
     */
    public Mono<Void> blockUserSendMsgToUser(String fromUser, String toUser) {
        return this.sendMsgToUser.blockUser(fromUser, toUser);
    }

    /**
     * 解除用户禁言，恢复向这个用户发消息。
     * <p>
     * 要恢复 userA 给 userB发送消息：
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().unblockUserSendMsgToUser("userA", "userB").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param fromUser 被阻止的用户的用户名
     * @param toUser   接受消息的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E7%A7%BB%E9%99%A4%E9%BB%91%E5%90%8D%E5%8D%95">解除用户禁言</a>
     */
    public Mono<Void> unblockUserSendMsgToUser(String fromUser, String toUser) {
        return this.sendMsgToUser.unblockUser(fromUser, toUser);
    }

    /**
     * 用户账号禁用，阻止该用户登录。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().blockUserLogin("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被阻止的用户的用户名
     * @return 成功或失败
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E7%94%A8%E6%88%B7%E8%B4%A6%E5%8F%B7%E7%A6%81%E7%94%A8">用户账号禁用</a>
     */
    public Mono<Void> blockUserLogin(String username) {
        return this.blockUserLogin.blockUser(username);
    }

    /**
     * 用户账号解禁，恢复该用户登录。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().unblockUserLogin("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被阻止的用户的用户名
     * @return 成功或失败
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#">用户账号解禁</a>
     */
    public Mono<Void> unblockUserLogin(String username) {
        return this.blockUserLogin.unblockUser(username);
    }

    /**
     * 获取阻止进群的用户列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<EMBlock> blocks = service.block().getUsersBlockedJoinGroup("groupId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param groupId 群id
     * @return 被阻止进入的用户名
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E6%9F%A5%E8%AF%A2%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95">获取阻止进群列表</a>
     */
    public Flux<EMBlock> getUsersBlockedJoinGroup(String groupId) {
        return this.blockUserJoinGroup.getBlockedUsers(groupId);
    }

    /**
     * 阻止进群。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().blockUserJoinGroup("username", "groupId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被阻止的用户的用户名
     * @param groupId  群id
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7%E8%87%B3%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95">阻止进群</a>
     */
    public Mono<Void> blockUserJoinGroup(String username, String groupId) {
        return this.blockUserJoinGroup.blockUser(username, groupId);
    }

    /**
     * 解除阻止进群。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().unblockUserJoinGroup("username", "groupId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被阻止的用户的用户名
     * @param groupId  群id
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E4%BB%8E%E7%BE%A4%E7%BB%84%E9%BB%91%E5%90%8D%E5%8D%95%E7%A7%BB%E9%99%A4%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7"></a>
     */
    public Mono<Void> unblockUserJoinGroup(String username, String groupId) {
        return this.blockUserJoinGroup.unblockUser(username, groupId);
    }

    /**
     * 获取阻止进聊天室的用户列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<EMBlock> blocks = service.block().getUsersBlockedJoinRoom("roomId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @return 被阻止进入的用户名
     */
    public Flux<EMBlock> getUsersBlockedJoinRoom(String roomId) {
        return this.blockUserJoinRoom.getBlockedUsers(roomId);
    }

    /**
     * 阻止进聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().blockUserJoinRoom("username", "roomId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被阻止的用户的用户名
     * @param roomId   聊天室id
     * @return 成功或错误
     */
    public Mono<Void> blockUserJoinRoom(String username, String roomId) {
        return this.blockUserJoinRoom.blockUser(username, roomId);
    }

    /**
     * 解除阻止进聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().unblockUserJoinRoom("username", "roomId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被阻止的用户的用户名
     * @param roomId   聊天室id
     * @return 成功或错误
     */
    public Mono<Void> unblockUserJoinRoom(String username, String roomId) {
        return this.blockUserJoinRoom.unblockUser(username, roomId);
    }

    /**
     * 获取群禁言列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<EMBlock> blocks = service.block().getUsersBlockedSendMsgToGroup("groupId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param groupId 群id
     * @return 每个禁言或错误.
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%A6%81%E8%A8%80%E5%88%97%E8%A1%A8">获取禁言列表</a>
     */
    public Flux<EMBlock> getUsersBlockedSendMsgToGroup(String groupId) {
        return this.blockUserSendMsgToGroup.getBlockedUsers(groupId);
    }

    /**
     * 添加群禁言。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().blockUserSendMsgToGroup("username", "groupId", Duration.ofMillis(6000)).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被禁言的用户的用户名
     * @param groupId  群id
     * @param duration 禁言多长时间，为null则永久禁言
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E7%A6%81%E8%A8%80">添加禁言</a>
     */
    public Mono<Void> blockUserSendMsgToGroup(String username, String groupId, Duration duration) {
        return this.blockUserSendMsgToGroup.blockUser(username, groupId, duration);
    }

    /**
     * 解除群禁言。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().unblockUserSendMsgToGroup("username", "groupId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被禁言的用户的用户名
     * @param groupId  群id
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E7%A6%81%E8%A8%80">移除禁言</a>
     */
    public Mono<Void> unblockUserSendMsgToGroup(String username, String groupId) {
        return this.blockUserSendMsgToGroup.unblockUser(username, groupId);
    }

    /**
     * 获取聊天室禁言列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<EMBlock> blocks = service.block().listUsersBlockedSendMsgToRoom("roomId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @return 每个禁言或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E7%A6%81%E8%A8%80%E5%88%97%E8%A1%A8">获取禁言列表</a>
     */
    public Flux<EMBlock> listUsersBlockedSendMsgToRoom(String roomId) {
        return this.listUsersBlockedSendMsgToRoom.all(roomId);
    }

    /**
     * 添加聊天室禁言。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().blockUserSendMsgToRoom("username", "roomId", Duration.ofMillis(6000)).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被禁言的用户的用户名
     * @param roomId   聊天室id
     * @param duration 禁言时长，为null则永久禁言
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E7%A6%81%E8%A8%80">添加禁言</a>
     */
    public Mono<Void> blockUserSendMsgToRoom(String username, String roomId, Duration duration) {
        return this.blockUserSendMsgToRoom.single(username, roomId, duration);
    }

    /**
     * 解除聊天室禁言。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.block().unblockUserSendMsgToRoom("username", "roomId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 被禁言的用户的用户名
     * @param roomId   聊天室id
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E7%A6%81%E8%A8%80">移除禁言</a>
     */
    public Mono<Void> unblockUserSendMsgToRoom(String username, String roomId) {
        return this.unblockUserSendMsgToRoom.single(username, roomId);
    }
}
