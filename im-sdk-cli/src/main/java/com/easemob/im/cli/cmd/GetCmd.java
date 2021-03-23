package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Command(name = "get", description = "Get a resource.")
public class GetCmd {

    @Autowired
    private EMService service;

    @Value("${im.cli.download.attachment-dir}")
    private Path attachmentDir;

    @Value("${im.cli.download.history-dir}")
    private Path historyDir;

    @Command(name = "attachment", description = "Download attachment by id.")
    public void attachment(@Parameters String id) {
        this.service.attachment().downloadFile(id, this.attachmentDir, id)
                .doOnSuccess(downloaded -> System.out.println(String.format("downloaded: %s", downloaded.toString())))
                .doOnError(error -> System.out.println(String.format("error: %s", error.getMessage())))
                .onErrorResume(EMException.class, error -> Mono.empty())
                .block();
    }


    @Command(name = "history", description = "Get the history file uri by time")
    public void history(@Parameters(description = "The ISO8601 date time. e.g. 2020-12-12T13:00") String datetime,
                        @Option(names = {"--download"}, description = "Download the file if specified. The file is compressed, use `zless` to read it.") boolean download) {

        ZonedDateTime localDatetime = ZonedDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault()));
        Instant instant = localDatetime.toInstant();

        if (!download) {
            this.service.message().getHistoryAsUri(instant)
                    .doOnNext(uri -> System.out.println(String.format("uri: %s", uri)))
                    .doOnError(err -> System.out.println(String.format("error: %s", err.getMessage())))
                    .block();
        } else {
            this.service.message().getHistoryAsLocalFile(instant, this.historyDir, datetime.replaceAll("[-T:]", "_") + ".gz")
                    .doOnNext(uri -> System.out.println(String.format("uri: %s", uri)))
                    .doOnError(err -> System.out.println(String.format("error: %s", err.getMessage())))
                    .block();
        }
    }

    @Command(name = "user", description = "Get a user's info or list users.")
    public void user(@Parameters(arity = "0..1", description = "The username, if miss, list users") String username,
                     @ArgGroup(exclusive = false) LimitArgGroup limitArgGroup) {
        if (StringUtils.hasText(username)) {
            this.service.user().get(username)
                    .doOnNext(user -> {
                        System.out.println("user: " + user.getUsername());
                        System.out.println("canLogin: " + user.getCanLogin());
                    }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (limitArgGroup != null) {
            service.user().listUsers(limitArgGroup.limit, limitArgGroup.cursor)
                    .doOnNext(emPage -> {
                        emPage.getValues().forEach(System.out::println);
                        System.out.println("cursor: " + emPage.getCursor());
                    }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            service.user().listAllUsers()
                    .doOnNext(System.out::println).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    @Command(name = "contact", description = "List contacts of a user.")
    public void contact(@Parameters(description = "the user") String username) {
        this.service.contact().list(username)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .blockLast();
    }

    @Command(name = "group", description = "Get a group's info or list groups")
    public void group(@Parameters(arity = "0..1", description = "the group's id, list all groups if missing") String groupId,
                      @Option(names = "--have-user", description = "search groups have this user, not support limit and cursor") String username,
                      @ArgGroup(exclusive = false, heading = "If missing, list all groups\n") LimitArgGroup limitArgGroup) {
        if (StringUtils.hasText(groupId)) {
            this.service.group().getGroup(groupId)
                    .doOnSuccess(group -> {
                        System.out.println("groupId: " + group.getGroupId());
                        System.out.println("name: " + group.getName());
                        System.out.println("description: " + group.getDescription());
                        System.out.println("\tisPublic: " + group.getIsPublic());
                        System.out.println("\tmaxMembers: " + group.getMaxMembers());
                        System.out.println("\tcanMemberInviteOthers: " + group.getCanMemberInviteOthers());
                        System.out.println("\towner: " + group.getOwner());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
            this.service.group().getGroupAnnouncement(groupId)
                    .doOnSuccess(msg -> System.out.println("announcement: " + msg))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (StringUtils.hasText(username)) {
            this.service.group().listGroupsUserJoined(username)
                    .doOnNext(gid -> System.out.println("groupId: " + gid))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limitArgGroup != null) {
            this.service.group().listGroups(limitArgGroup.limit, limitArgGroup.cursor)
                    .doOnNext(emPage -> {
                        emPage.getValues().forEach(System.out::println);
                        System.out.println("cursor: " + emPage.getCursor());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().listAllGroups()
                    .doOnNext(gid -> {
                        System.out.println("groupId: " + gid);
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    @Command(name = "room", description = "Get a room's info or list rooms")
    public void room(@Parameters(arity = "0..1", description = "the room's id, if miss, list rooms") String roomId,
                     @Option(names = "--have-user", description = "search rooms have this user, not support limit and cursor") String username,
                     @ArgGroup(exclusive = false, heading = "If missing, list all rooms\n") LimitArgGroup limitArgGroup) {
        if (StringUtils.hasText(roomId)) {
            this.service.room().getRoom(roomId)
                    .doOnSuccess(room -> {
                        System.out.println("roomId: " + room.id());
                        System.out.println("\tname: " + room.name());
                        System.out.println("\tdescription: " + room.description());
                        System.out.println("\tmaxMembers: " + room.maxMembers());
                        System.out.println("\tcanMemberInviteOthers: " + !room.needApprove());
                        System.out.println("\towner: " + room.owner());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (StringUtils.hasText(username)) {
            this.service.room().listRoomsUserJoined(username)
                    .doOnNext(rid -> System.out.println("roomId: " + rid))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limitArgGroup != null) {
            this.service.room().listRooms(limitArgGroup.limit, limitArgGroup.cursor)
                    .doOnNext(emPage -> {
                        emPage.getValues().forEach(System.out::println);
                        System.out.println("cursor: " + emPage.getCursor());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.room().listRoomsAll()
                    .doOnNext(rid -> {
                        System.out.println("roomId: " + rid);
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    private static class BlockArgGroup {
        @Option(names = {"--msg-to-user"}, description = "list users who can not send message to this user")
        String msgToUsername;

        @Option(names = {"--msg-to-group"}, description = "list users who can not send message in this group")
        String msgToGroupId;

        @Option(names = {"--msg-to-room"}, description = "list users who can not join this room")
        String msgToRoomId;

        @Option(names = {"--join-group"}, description = "list users who can not join this group")
        String joinGroupId;

        @Option(names = {"--login"}, description = "list users who can not login")
        boolean login;
    }

    @Command(name = "block", description = "List blocked user.")
    public void block(@ArgGroup(multiplicity = "1", exclusive = false) BlockArgGroup argGroup) {
        if (StringUtils.hasText(argGroup.msgToUsername)) {
            this.service.block().getUsersBlockedFromSendMsgToUser(argGroup.msgToUsername)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
        if (StringUtils.hasText(argGroup.msgToGroupId)) {
            this.service.block().getUsersBlockedSendMsgToGroup(argGroup.msgToGroupId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
        if (StringUtils.hasText(argGroup.msgToRoomId)) {
            this.service.block().listUsersBlockedSendMsgToRoom(argGroup.msgToRoomId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
        if (StringUtils.hasText(argGroup.joinGroupId)) {
            this.service.block().getUsersBlockedJoinGroup(argGroup.joinGroupId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
        if (argGroup.login) {
            System.out.println("Not implemented");
        }
    }

    private static class MemberArgGroup {
        @Option(names = "--group", description = "list this group members")
        String groupId;

        @Option(names = "--room", description = "list this room members")
        String roomId;
    }

    @Command(name = "member", description = "List group or room members, not include the owner and admins.")
    public void member(@ArgGroup(multiplicity = "1") MemberArgGroup memberArgGroup,
                       @ArgGroup(exclusive = false) LimitArgGroup limitArgGroup) {
        if (memberArgGroup.roomId != null) {
            if (limitArgGroup != null) {
                this.service.room().listRoomMembers(memberArgGroup.roomId, limitArgGroup.limit, limitArgGroup.cursor)
                        .doOnSuccess(emPage -> {
                            emPage.getValues().forEach(System.out::println);
                            System.out.println("cursor: " + emPage.getCursor());
                        })
                        .doOnError(err -> System.out.println("error: " + err.getMessage()))
                        .onErrorResume(EMException.class, ignore -> Mono.empty())
                        .block();
            } else {
                this.service.room().listRoomMembersAll(memberArgGroup.roomId)
                        .doOnNext(System.out::println)
                        .doOnError(err -> System.out.println("error: " + err.getMessage()))
                        .onErrorResume(EMException.class, ignore -> Mono.empty())
                        .blockLast();
            }
        } else {
            if (limitArgGroup != null) {
                this.service.group().listGroupMembers(memberArgGroup.groupId, limitArgGroup.limit, limitArgGroup.cursor)
                        .doOnSuccess(emPage -> {
                            emPage.getValues().forEach(System.out::println);
                            System.out.println("cursor: " + emPage.getCursor());
                        })
                        .doOnError(err -> System.out.println("error: " + err.getMessage()))
                        .onErrorResume(EMException.class, ignore -> Mono.empty())
                        .block();
            } else {
                this.service.group().listAllGroupMembers(memberArgGroup.groupId)
                        .doOnNext(System.out::println)
                        .doOnError(err -> System.out.println("error: " + err.getMessage()))
                        .onErrorResume(EMException.class, ignore -> Mono.empty())
                        .blockLast();
            }
        }
    }

    private static class MessageArgGroup {
        @Option(names = "--count", description = "count message")
        boolean count;

        @Option(names = "--status", description = "get one message status")
        String messageId;
    }

    @Command(name = "message", description = "List or count messages.")
    public void message(@ArgGroup(multiplicity = "1") MessageArgGroup argGroup,
                        @Option(names = "--missed", description = "list this user's missed messages") String username) {
        if (argGroup.count) {
            if (StringUtils.hasText(username)) {
                this.service.message().countMissedMessages(username)
                        .doOnNext(msg -> {
                            System.out.printf("%s : %s \n", msg.getQueueName(), msg.getMessageCount());
                        })
                        .doOnError(err -> System.out.println("error: " + err.getMessage()))
                        .onErrorResume(EMException.class, ignore -> Mono.empty())
                        .blockLast();
            }
        } else if (StringUtils.hasText(argGroup.messageId)) {
            System.out.println("Not implemented.");
        }
    }

    private static class AdminArgGroup {
        @Option(names = "--group", description = "list group's admin")
        String groupId;

        @Option(names = "--room", description = "list room's admin")
        String roomId;

        @Option(names = "--super", description = "list super admin")
        boolean superAdmin;
    }

    @Command(name = "admin", description = "List admin")
    public void admin(@ArgGroup(multiplicity = "1") AdminArgGroup argGroup) {
        if (argGroup.groupId != null) {
            this.service.group().listGroupAdmins(argGroup.groupId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (argGroup.roomId != null) {
            this.service.room().listRoomAdminsAll(argGroup.roomId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (argGroup.superAdmin) {
            this.service.room().listRoomSuperAdminsAll()
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    private static class LimitArgGroup {
        @Option(names = "--limit", description = "the limit", required = true)
        Integer limit;

        /**
         * 游标，第一次请求分页不需要指定，之后分页cursor用上一次请求返回的cursor
         */
        @Option(names = "--cursor", description = "the cursor in latest response.")
        String cursor;
    }
}
