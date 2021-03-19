package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.easemob.im.server.api.message.send.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

@Component
@Command(name = "create", description = "Create a resource.")
public class CreateCmd {

    @Autowired
    private EMService service;

    @Command(name = "attachment", description = "Create an attachment.")
    public void attachment(@Option(names = "-f", description = "from file") Path file) {
        this.service.attachment().uploadFile(file)
                .doOnNext(id -> System.out.println(String.format("id: %s", id)))
                .doOnError(err -> System.out.println(String.format("error: %s", err.getMessage())))
                .onErrorResume(EMException.class, err -> Mono.empty())
                .block();
    }

    private static class BlockArgGroup {
        @Option(names = {"--msg-to-user"}, description = "block user send message to this user")
        String msgToUsername;

        @Option(names = {"--msg-to-group"}, description = "block user send message to this group")
        String msgToGroupId;

        @Option(names = {"--msg-to-room"}, description = "block user send message to this room")
        String msgToRoomId;

        @Option(names = {"--join-group"}, description = "block user to join group")
        String joinGroupId;

        @Option(names = {"--login"}, description = "block user to login")
        boolean login;

        @Option(names = "--duration", description = "block duration, required by block user send msg to group or room")
        Duration duration;
    }

    @Command(name = "block", description = "Block user from resource.")
    public void block(@Parameters(description = "user to block") String username,
                      @ArgGroup(multiplicity = "1", exclusive = false) BlockArgGroup argGroup) {
        if (StringUtils.hasText(argGroup.msgToUsername)) {
            this.service.block().blockUserSendMsgToUser(username, argGroup.msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.msgToGroupId) && argGroup.duration != null) {
            this.service.block().blockUserSendMsgToGroup(username, argGroup.msgToGroupId, argGroup.duration)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.msgToRoomId) && argGroup.duration != null) {
            this.service.block().blockUserSendMsgToRoom(argGroup.msgToUsername, argGroup.msgToRoomId, argGroup.duration)
                    .doOnSuccess(ig -> System.out.println("done."))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.joinGroupId)) {
            this.service.block().blockUserJoinGroup(username, argGroup.joinGroupId)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (argGroup.login) {
            this.service.block().blockUserLogin(username)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    @Command(name = "user", description = "Create a user.", mixinStandardHelpOptions = true)
    public void user(@Parameters(description = "the username") String username,
                     @Parameters(description = "the password") String password) {
        service.user().create(username, password)
                .doOnSuccess(ignore -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "contact", description = "Add a contact to the user.")
    public void contact(@Parameters(description = "the user's username") String user,
                        @Parameters(description = "the contact's username") String contact) {
        this.service.contact().add(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "group", description = "Create a group.\n" +
            "Public(by default) groups can be listed using Android, iOS, Web SDKs. So that end user can discover and join public groups.\n" +
            "Private groups can NOT be listed using Android, iOS, Web SDKs.")
    public void group(@Parameters(arity = "1", description = "the member's username list") List<String> members,
                      @Option(names = "--owner", required = true, description = "the owner's username") String owner,
                      @Option(names = "--private", description = "create a private group") boolean isPrivate,
                      @Option(names = "--max-members", defaultValue = "200", description = "max number of members") int maxMembers,
                      @Option(names = "--can-member-invite", description = "can member invite others to join") boolean canMemberInvite) {
        if (isPrivate) {
            this.service.group().createPrivateGroup(owner, members, maxMembers, canMemberInvite)
                    .doOnSuccess(groupId -> System.out.println("group: " + groupId))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().createPublicGroup(owner, members, maxMembers, !canMemberInvite)
                    .doOnSuccess(groupId -> System.out.println("group: " + groupId))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class MemberArgGroup {
        @Option(names = "--to-group", description = "add user to this group")
        String toGroup;

        @Option(names = "--to-room", description = "add user to this room")
        String toRoom;
    }

    @Command(name = "member", description = "Add user to group or room.")
    public void member(@Parameters(description = "the user") String username,
                       @ArgGroup(multiplicity = "1") MemberArgGroup argGroup) {
        if (StringUtils.hasText(argGroup.toGroup)) {
            this.service.group().addGroupMember(argGroup.toGroup, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (StringUtils.hasText(argGroup.toRoom)) {
            this.service.room().addRoomMember(argGroup.toRoom, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class MessageArgGroup {
        @Option(names = "--to-user", description = "send message to this user")
        String toUsername;

        @Option(names = "--to-group", description = "send message to this group")
        String toGroupId;

        @Option(names = "--to-room", description = "send message to this room")
        String toRoomId;
    }

    @Command(name = "message", description = "Send messages.")
    public void message(@Parameters(description = "message sender") String sender,
                        @ArgGroup(multiplicity = "1", heading = "To whom.") MessageArgGroup argGroup,
                        @Option(names = "--text", description = "send this text message") String text) {
        // TODO 目前只支持控制台发送文本，后续将支持从文件读取以及多种消息类型。
        if (text != null) {
            SendMessage.RouteSpec routeSpec = this.service.message().send().fromUser(sender);
            SendMessage.MessageSpec messageSpec;
            if (argGroup.toUsername != null) {
                messageSpec = routeSpec.toUser(argGroup.toUsername);
            } else if (argGroup.toGroupId != null) {
                messageSpec = routeSpec.toGroup(argGroup.toGroupId);
            } else {
                messageSpec = routeSpec.toRoom(argGroup.toRoomId);
            }
            messageSpec.text(emTextMessage -> emTextMessage.text(text)).send()
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class AdminArgGroup {
        @Option(names = "--group", description = "add a group admin")
        String groupId;

        @Option(names = "--room", description = "add a room admin")
        String roomId;
    }

    @Command(name = "admin", description = "Add a group or room's admin")
    public void admin(@Parameters(description = "admin username") String username,
                      @ArgGroup(multiplicity = "1") AdminArgGroup argGroup) {
        if (argGroup.groupId != null) {
            this.service.group().addGroupAdmin(argGroup.groupId, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            // TODO 缺少 addRoomAdmin API
//            this.service.room().addRoomAdmin(argGroup.roomId, username)
//                    .doOnNext(System.out::println)
//                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
//                    .onErrorResume(EMException.class, ignore -> Mono.empty())
//                    .blockLast();
            System.out.println("Not implemented.");
        }
    }
}
