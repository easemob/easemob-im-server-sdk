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
                     @ArgGroup(exclusive = false) PageArgGroup pageArgGroup) {
        if (StringUtils.hasText(username)) {
            this.service.user().get(username)
                    .doOnNext(user -> {
                        System.out.println("user: " + user.getUsername());
                        System.out.println("canLogin: " + user.getCanLogin());
                    }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (pageArgGroup != null) {
            service.user().listUsers(pageArgGroup.limit, pageArgGroup.cursor)
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
    public void group(@Parameters(description = "the group's id, if miss, list groups") String groupId,
                      @Option(names = "--have-user", description = "search groups have this user") String username,
                      @ArgGroup(exclusive = false, heading = "Only for list all groups:\n") PageArgGroup pageArgGroup) {
        if (StringUtils.hasText(groupId)) {
            this.service.group().getGroup(groupId)
                    .doOnSuccess(group -> {
                        System.out.println("group: " + group.getGroupId());
                        System.out.println("\tisPublic: " + group.getIsPublic());
                        System.out.println("\tmaxMembers: " + group.getMaxMembers());
                        System.out.println("\tneedApproveToJoin: " + group.getNeedApproveToJoin());
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
                    .doOnNext(gid -> System.out.println("group: " + gid))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (pageArgGroup != null) {
            this.service.group().listGroups(pageArgGroup.limit, pageArgGroup.cursor)
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
                        System.out.println("group: " + gid);
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
            System.out.println("Not implemented");
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

    @Command(name = "member", description = "List group or room members.")
    public void member(@ArgGroup(multiplicity = "1") MemberArgGroup memberArgGroup,
                       @ArgGroup(exclusive = false) PageArgGroup pageArgGroup) {
        if (memberArgGroup.roomId != null) {
            if (pageArgGroup != null) {
                this.service.room().listRoomMembers(memberArgGroup.roomId, pageArgGroup.limit, pageArgGroup.cursor)
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
            if (pageArgGroup != null) {
                this.service.group().listGroupMembers(memberArgGroup.groupId, pageArgGroup.limit, pageArgGroup.cursor)
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
    }

    @Command(name = "admin", description = "List a group or room's admin")
    public void admin(@ArgGroup(multiplicity = "1") AdminArgGroup argGroup) {
        if (argGroup.groupId != null) {
            this.service.group().listGroupAdmins(argGroup.groupId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else {
            this.service.room().listRoomAdminsAll(argGroup.roomId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    private static class PageArgGroup {
        @Option(names = "--limit", description = "the limit", required = true)
        Integer limit;

        @Option(names = "--cursor", description = "the cursor", required = true)
        String cursor;
    }
}
