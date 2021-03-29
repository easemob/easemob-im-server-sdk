package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import reactor.core.publisher.Mono;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;


@Component
@Command(name = "delete", description = "Delete a resource.", mixinStandardHelpOptions = true)
public class DeleteCmd {

    @Autowired
    private EMService service;

    private static class UnBlockArgGroup {
        @Option(names = {"--msg-to-user"}, description = "unblock user send message to this user")
        String msgToUsername;

        @Option(names = {"--msg-to-group"}, description = "unblock user send message to this group")
        String msgToGroupId;

        @Option(names = {"--msg-to-room"}, description = "unblock user send message to this room")
        String msgToRoomId;

        @Option(names = {"--join-group"}, description = "unblock user to join group")
        String joinGroupId;

        @Option(names = {"--join-room"}, description = "unblock user to join room")
        String joinRoomId;

        @Option(names = {"--login"}, description = "unblock user to login")
        boolean login;
    }

    @Command(name = "block", description = "Unblock user from resource.", mixinStandardHelpOptions = true)
    public void block(@Parameters(description = "user to unblock") String username,
                      @ArgGroup(multiplicity = "1", exclusive = false) UnBlockArgGroup argGroup) {
        if (StringUtils.hasText(argGroup.msgToUsername)) {
            this.service.block().unblockUserSendMsgToUser(username, argGroup.msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.msgToGroupId)) {
            this.service.block().unblockUserSendMsgToGroup(username, argGroup.msgToGroupId)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.msgToRoomId)) {
            this.service.block().unblockUserSendMsgToRoom(username, argGroup.msgToRoomId)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.joinGroupId)) {
            this.service.block().unblockUserJoinGroup(username, argGroup.joinGroupId)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (argGroup.joinRoomId != null) {
            this.service.block().unblockUserJoinRoom(username, argGroup.joinRoomId)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (argGroup.login) {
            this.service.block().unblockUserLogin(username)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class DeleteUserArgGroup {
        @Parameters(description = "delete one user")
        String username;

        @Option(names = {"--all"}, description = "delete all users")
        boolean all;
    }

    @Command(name = "user", description = "Delete a user.", mixinStandardHelpOptions = true)
    public void user(@ArgGroup(multiplicity = "1") DeleteUserArgGroup argGroup) {
        if (argGroup.all) {
            this.service.user().deleteAll()
                    .doOnNext(user -> System.out.println("user " + user + " deleted"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else {
            this.service.user().delete(argGroup.username)
                    .doOnSuccess(user -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    @Command(name = "contact", description = "Remove a contact from the user.", mixinStandardHelpOptions = true)
    public void contact(@Parameters(description = "the user's username") String user,
                        @Parameters(description = "the contact's username") String contact) {
        this.service.contact().remove(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "group", description = "Destroy a group.\n" +
            "Messages will be destroyed with the group, while the chat history is reserved.", mixinStandardHelpOptions = true)
    public void group(@Parameters(description = "the group id") String groupId) {
        this.service.group().destroyGroup(groupId)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "room", description = "Destroy a room.\n" +
            "Messages will be destroyed with the room, while the chat history is reserved.", mixinStandardHelpOptions = true)
    public void room(@Parameters(description = "the room id") String roomId) {
        this.service.room().destroyRoom(roomId)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "session", description = "Force user logout, default to logout all devices if missing --device", mixinStandardHelpOptions = true)
    public void session(@Parameters(description = "the username") String username,
                        @Option(names = "--device", description = "logout specific device") String deviceName) {
        if (StringUtils.hasText(deviceName)) {
            this.service.user().forceLogoutOneDevice(username, deviceName)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.user().forceLogoutAllDevices(username)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class MemberArgGroup {
        @Option(names = "--from-group", description = "remove user from this group")
        String fromGroup;

        @Option(names = "--from-room", description = "remove user from this room")
        String fromRoom;
    }

    @Command(name = "member", description = "Remove user from group or room.", mixinStandardHelpOptions = true)
    public void member(@Parameters(description = "the user") String username,
                       @ArgGroup(multiplicity = "1") MemberArgGroup argGroup) {
        if (StringUtils.hasText(argGroup.fromGroup)) {
            this.service.group().removeGroupMember(argGroup.fromGroup, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (StringUtils.hasText(argGroup.fromRoom)) {
            this.service.room().removeRoomMember(argGroup.fromRoom, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class AdminArgGroup {
        @Option(names = "--group", description = "demote a group admin")
        String groupId;

        @Option(names = "--room", description = "demote a room admin")
        String roomId;

        @Option(names = "--super", description = "demote a super admin")
        String superAdminUsername;
    }

    @Command(name = "admin", description = "Demote a admin", mixinStandardHelpOptions = true)
    public void admin(@Parameters(description = "admin username") String username,
                      @ArgGroup(multiplicity = "1") AdminArgGroup argGroup) {
        if (argGroup.groupId != null) {
            this.service.group().removeGroupAdmin(argGroup.groupId, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (argGroup.roomId != null) {
            this.service.room().demoteRoomAdmin(argGroup.roomId, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.room().demoteRoomSuperAdmin(argGroup.superAdminUsername)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }
}
