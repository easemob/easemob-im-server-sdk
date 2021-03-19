package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public void attachment(@Parameters(arity = "1") String id) {
        this.service.attachment().downloadFile(id, this.attachmentDir, id)
                .doOnSuccess(downloaded -> System.out.println(String.format("downloaded: %s", downloaded.toString())))
                .doOnError(error -> System.out.println(String.format("error: %s", error.getMessage())))
                .onErrorResume(EMException.class, error -> Mono.empty())
                .block();
    }

    @Command(name = "history", description = "Get the history file uri by time")
    public void history(@Parameters(arity = "1", description = "The ISO8601 date time. e.g. 2020-12-12T13:00") String datetime,
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

    @Command(name = "user", description = "Get user info.", mixinStandardHelpOptions = true)
    public void user(@Parameters(index = "0", description = "The username") String username) {
        this.service.user().get(username)
                .doOnNext(user -> {
                    System.out.println("user: " + user.getUsername());
                    System.out.println("canLogin: " + user.getCanLogin());
                }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "user-list", description = "List users. By default all users are listed.\nUse --limit and --cursor to control ", mixinStandardHelpOptions = true)
    public void userList(@Option(names = {"--blocked-by-user"}, description = "to list users blocked from sending message to this user") String blockedByUser,
                         @Option(names = {"--limit"}, description = "to limit") Integer limit,
                         @Option(names = {"--cursor"}, description = "the cursor") String cursor) {

        if (blockedByUser != null) {
            service.block().getUsersBlockedFromSendMsgToUser(blockedByUser)
                    .doOnNext(emBlock -> System.out.println("user: " + emBlock.getUsername()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limit != null) {
            service.user().listUsers(limit, cursor)
                    .doOnNext(page -> {
                        System.out.println("cursor: " + page.getCursor());
                        System.out.println("users:");
                        for (String user : page.getValues()) {
                            System.out.println("\t" + user);
                        }
                    }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            service.user().listAllUsers()
                    .doOnNext(user -> {
                        System.out.println(user);
                    }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    @Command(name = "contact-list", description = "List contacts of a user.")
    public void contactList(@Parameters(index = "0", description = "the user's username") String username) {
        this.service.contact().list(username)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .blockLast();
    }

    @Command(name = "group", description = "Get a group's info.")
    public void group(@Parameters(index = "0", description = "the group's id") String groupId,
                      @Option(names = {"--settings"}, description = "get settings") boolean settings,
                      @Option(names = {"--owner"}, description = "get owner") boolean owner,
                      @Option(names = {"--members"}, description = "get members") boolean members,
                      @Option(names = {"--announcement"}, description = "get announcement") boolean announcement) {
        this.service.group().getGroup(groupId)
                .doOnSuccess(group -> {
                    System.out.println("group: " + group.getGroupId());
                    if (settings) {
                        System.out.println("\tisPublic: " + group.getIsPublic());
                        System.out.println("\tmaxMembers: " + group.getMaxMembers());
                        System.out.println("\tneedApproveToJoin: " + group.getNeedApproveToJoin());
                        System.out.println("\tcanMemberInviteOthers: " + group.getCanMemberInviteOthers());
                    }
                    if (owner) {
                        System.out.println("\towner: " + group.getOwner());
                    }
                    if (members) {
                        System.out.println("\tmembers: [");
                        for (String member : group.getMembers()) {
                            System.out.println("\t\t" + member);
                        }
                        System.out.println("\t]");
                    }
                })
                .then(Mono.defer(() -> {
                    if (announcement) {
                        return this.service.group().getGroupAnnouncement(groupId)
                                .doOnSuccess(msg -> System.out.println("announcement: " + msg));
                    }
                    return Mono.empty();
                }))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    // TODO: add api to list public groups
    @Command(name = "group-list", description = "List groups.")
    public void groupList(@Option(names = {"--user"}, description = "search groups joined by this username") String username,
                          @Option(names = {"--limit"}, description = "the limit") Integer limit,
                          @Option(names = {"--cursor"}, description = "the cursor") String cursor) {
        if (StringUtils.hasText(username)) {
            this.service.group().listGroupsUserJoined(username)
                    .doOnNext(groupId -> System.out.println("group: " + groupId))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limit != null) {
            this.service.group().listGroups(limit, cursor)
                    .doOnNext(page -> {
                        List<String> groupIds = page.getValues();
                        for (int i = 0; i < groupIds.size(); i++) {
                            System.out.println("group: " + groupIds.get(i));
                        }
                        System.out.println("cursor: " + page.getCursor());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().listAllGroups()
                    .doOnNext(groupId -> {
                        System.out.println("group: " + groupId);
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    @Command(name = "block", description = "List block list for user or group")
    public void block(@Option(names = {"--msg-to-user"}, description = "list user's block list") String msgToUsername,
                      @Option(names = {"--msg-to-group"}, description = "list users who can not send message in this group") String msgToGroupId,
                      @Option(names = {"--join-group"}, description = "list users who can not join this group") String joinGroupId) {
        if (!StringUtils.hasText(msgToUsername) && !StringUtils.hasText(msgToGroupId) && !StringUtils.hasText(joinGroupId)) {
            System.out.println("must specify one of option, e.g. --msg-to-user, --msg-to-group or --join-group");
            return;
        }
        if (StringUtils.hasText(msgToUsername)) {
            this.service.block().getUsersBlockedFromSendMsgToUser(msgToUsername)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
        if (StringUtils.hasText(msgToGroupId)) {
            this.service.block().getUsersBlockedSendMsgToGroup(msgToGroupId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
        if (StringUtils.hasText(joinGroupId)) {
            this.service.block().getUsersBlockedJoinGroup(joinGroupId)
                    .doOnNext(System.out::println)
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }
}
