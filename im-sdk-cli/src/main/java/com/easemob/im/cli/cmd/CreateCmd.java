package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.easemob.im.server.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Command(name = "create", description = "Create a resource.", mixinStandardHelpOptions = true)
public class CreateCmd {

    @Autowired
    private EMService service;

    @Command(name = "attachment", description = "Create an attachment.", mixinStandardHelpOptions = true)
    public void attachment(@Option(names = "-f", description = "from file") Path file) {
        this.service.attachment().uploadFile(file)
                .doOnNext(resp ->
                        System.out.printf("id = %s\nurl = %s\nsecret = %s\n", resp.getId(), resp.getUrl(), resp.getSecret()))
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

        @Option(names = {"--join-room"}, description = "block user to join room")
        String joinRoomId;

        @Option(names = {"--login"}, description = "block user to login")
        boolean login;

        @Option(names = "--duration", description = "block duration, block user forever if missing")
        Duration duration;
    }

    @Command(name = "block", description = "Block user from resource.", mixinStandardHelpOptions = true)
    public void block(@Parameters(description = "user to block") String username,
                      @ArgGroup(multiplicity = "1", exclusive = false) BlockArgGroup argGroup) {
        if (StringUtils.hasText(argGroup.msgToUsername)) {
            this.service.block().blockUserSendMsgToUser(username, argGroup.msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.msgToGroupId)) {
            this.service.block().blockUserSendMsgToGroup(username, argGroup.msgToGroupId, argGroup.duration)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(argGroup.msgToRoomId)) {
            this.service.block().blockUserSendMsgToRoom(username, argGroup.msgToRoomId, argGroup.duration)
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
        if (argGroup.joinRoomId != null) {
            this.service.block().blockUserJoinRoom(username, argGroup.joinRoomId)
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

    @Command(name = "contact", description = "Add a contact to the user.", mixinStandardHelpOptions = true)
    public void contact(@Parameters(description = "the user's username") String user1,
                        @Parameters(description = "the contact's username") String contact) {
        this.service.contact().add(user1, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "group", description = "Create a group.\n" +
            "Public(by default) groups can be listed using Android, iOS, Web SDKs. So that end user can discover and join public groups.\n" +
            "Private groups can NOT be listed using Android, iOS, Web SDKs.", mixinStandardHelpOptions = true)
    public void group(@Parameters(arity = "1", description = "the member's username list") List<String> members,
                      @Option(names = "--owner", required = true, description = "the owner's username") String owner,
                      @Option(names = "--name", required = true, description = "the group's name") String name,
                      @Option(names = "--description", required = true, description = "the group's description") String description,
                      @Option(names = "--private", description = "create a private group") boolean isPrivate,
                      @Option(names = "--max-members", defaultValue = "200", description = "max number of members") int maxMembers,
                      @Option(names = "--can-member-invite", description = "can member invite others to join") boolean canMemberInvite) {
        if (isPrivate) {
            this.service.group().createPrivateGroup(owner, name, description, members, maxMembers, canMemberInvite)
                    .doOnSuccess(groupId -> System.out.println("group: " + groupId))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().createPublicGroup(owner, name, description, members, maxMembers, !canMemberInvite)
                    .doOnSuccess(groupId -> System.out.println("group: " + groupId))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    @Command(name = "room", description = "Create a room.", mixinStandardHelpOptions = true)
    public void room(@Parameters(arity = "1", description = "the member's username list") List<String> members,
                     @Option(names = "--name", required = true, description = "the room name") String name,
                     @Option(names = "--owner", required = true, description = "the owner's username") String owner,
                     @Option(names = "--description", description = "the room's description", defaultValue = "") String description,
                     @Option(names = "--max-members", defaultValue = "200", description = "max number of members") int maxMembers) {
        this.service.room().createRoom(name, description, owner, members, maxMembers)
                .doOnSuccess(roomId -> System.out.println("roomId: " + roomId))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "sms", description = "send sms.", mixinStandardHelpOptions = true)
    public void sms(@Option(names = "--mobiles", split = "," ,required = true, description = "the receive sms mobiles") List<String> mobiles,
                     @Option(names = "--tid", required = true, description = "the sms template") String tid,
                     @Option(names = "--tmap", required = true, description = "the template variables") Map<String, String> tmap,
                     @Option(names = "--extendCode", description = "the extend code") String extendCode,
                     @Option(names = "--custom", description = "the user custom") String custom) {
        this.service.sms().send(mobiles, tid, tmap, extendCode, custom)
                .doOnSuccess(sendSms -> System.out.println("sendSms: " + sendSms))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    private static class MemberArgGroup {
        @Option(names = "--to-group", description = "add user to this group")
        String toGroup;

        @Option(names = "--to-room", description = "add user to this room")
        String toRoom;
    }

    @Command(name = "member", description = "Add user to group or room.", mixinStandardHelpOptions = true)
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

    private static class MessageToGroup {
        @Option(names = "--to-user", split = ",", description = "send message to these user, split each username by `,`")
        Set<String> toUsernames;

        @Option(names = "--to-group", split = ",", description = "send message to these groups, split each groupId by `,`")
        Set<String> toGroupIds;

        @Option(names = "--to-room", split = ",", description = "send message to these rooms, split each roomId by `,`")
        Set<String> toRoomIds;
    }

    private static class MessageTypeArgGroup {
        @Option(names = "--text", description = "text message, value is the ")
        String text;

        @Option(names = "--img", description = "send image message, value is image url")
        String img;

        @Option(names = "--audio", description = "send audio message, value is image url")
        String audio;

        @Option(names = "--video", description = "video message, value is video url")
        String video;

        @Option(names = "--loc", split = ":", description = "location message, value format is longitude:latitude:address")
        String[] loc;

        @Option(names = "--file", description = "file message, value is file url")
        String file;

        @Option(names = "--cmd", description = "command message, value format is key:value")
        String cmd;

        @Option(names = "--custom", description = "custom message, value format is event:extensionKey:extensionValue")
        String custom;
    }

    @Command(name = "message", description = "Send messages.", mixinStandardHelpOptions = true)
    public void message(
            @Option(names = "--from", description = "message sender username", required = true) String from,
            @ArgGroup(multiplicity = "1", heading = "To whom.\n") MessageToGroup to,
            @ArgGroup(multiplicity = "1", heading = "Message type\n") MessageTypeArgGroup msg,
            @Option(names = "--secret", description = "secret returned by attachment upload service") String secret,
            @Option(names = "--filename", description = "filename", defaultValue = "未命名") String filename,
            @Option(names = "--bytes", description = "file size, unit is B(byte)", defaultValue = "0") Integer bytes,
            @Option(names = "--duration", description = "video or audio duration, unit is s(second)", defaultValue = "0") Integer duration,
            @Option(names = "--ext", description = "message extension") Map<String, Object> extensions,
            @Option(names = "--param", description = "message extension") Map<String, Object> params) {
        EMMessage message;

        if (msg.text != null) {
            message = new EMTextMessage().text(msg.text);
        } else if (msg.img != null) {
            message = new EMImageMessage().displayName(filename).uri(URI.create(msg.img)).secret(secret).bytes(bytes);
        } else if (msg.audio != null) {
            message = new EMVoiceMessage().displayName(filename).uri(URI.create(msg.audio)).secret(secret).duration(duration).bytes(bytes);
        } else if (msg.video != null) {
            message = new EMVideoMessage().displayName(filename).uri(URI.create(msg.video)).secret(secret).duration(duration).bytes(bytes);
        } else if (msg.loc != null) {
            message = new EMLocationMessage().longitude(Double.parseDouble(msg.loc[0])).latitude(Double.parseDouble(msg.loc[1])).address(msg.loc[2]);
        } else if (msg.file != null) {
            message = new EMFileMessage().displayName(filename).uri(URI.create(msg.file)).secret(secret).bytes(bytes);
        } else if (msg.cmd != null) {
            message = new EMCommandMessage().action(msg.cmd).params(EMKeyValue.of(params));
        } else {
            message = new EMCustomMessage().customEvent(msg.custom).customExtensions(EMKeyValue.of(params));
        }
        String toType = to.toUsernames != null ? "users" : to.toGroupIds != null ? "chatgroups" : "chatrooms";
        Set<String> toIds = to.toUsernames != null ? to.toUsernames : to.toGroupIds != null ? to.toGroupIds : to.toRoomIds;
        this.service.message()
                .send(from, toType, toIds, message, EMKeyValue.of(extensions))
                .doOnSuccess(sentMessages -> {
                    sentMessages.getMessageIdsByEntityId().forEach((toId, messageId) -> {
                        System.out.printf("toId = %s : messageId = %s\n", toId, messageId);
                    });
                })
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    private static class AdminArgGroup {
        @Option(names = "--group", description = "promote the specified user to group admin, group admin is some user who can block user from joining or " +
                "sending messages in this group")
        String groupId;

        @Option(names = "--room", description = "promote the specified user to room admin, room admin is some user who can block user from joining or " +
                "sending message in this room")
        String roomId;

        @Option(names = "--super", description = "promote the specified user to super admin, super admin is some user who can create room")
        boolean superAdmin;
    }

    @Command(name = "admin", description = "Promote a admin.", mixinStandardHelpOptions = true)
    public void admin(@Parameters(description = "admin username") String username,
                      @ArgGroup(multiplicity = "1") AdminArgGroup argGroup) {
        if (argGroup.groupId != null) {
            this.service.group().addGroupAdmin(argGroup.groupId, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (argGroup.roomId != null) {
            this.service.room().promoteRoomAdmin(argGroup.roomId, username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (argGroup.superAdmin) {
            this.service.room().promoteRoomSuperAdmin(username)
                    .doOnSuccess(ig -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }
}
