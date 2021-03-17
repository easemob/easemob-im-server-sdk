package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupMember;
import com.easemob.im.server.model.EMUser;
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
        this.service.file().downloadFile(id, this.attachmentDir, id)
                .doOnSuccess(downloaded -> System.out.println(String.format("downloaded: %s", downloaded.toString())))
                .doOnError(error -> System.out.println(String.format("error: %s", error.getMessage())))
                .onErrorResume(EMException.class, error -> Mono.empty())
                .block();
    }

    @Command(name = "history", description = "Get the history file uri by time")
    public void getHistory(@Parameters(arity = "1", description = "The ISO8601 date time. e.g. 2020-12-12T13:00") String datetime,
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
                    .doOnNext(username -> System.out.println("user: " + username))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limit != null) {
            service.user().listUsers(limit, cursor)
                    .doOnNext(rsp -> {
                        System.out.println("cursor: " + rsp.getCursor());
                        System.out.println("users:");
                        for (EMUser user : rsp.getEMUsers()) {
                            System.out.println("\t" + user.getUsername());
                        }
                    }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            service.user().listAllUsers()
                    .doOnNext(user -> {
                        System.out.println(user.getUsername());
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
        this.service.group().getGroupDetails(groupId)
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
                        for (EMGroupMember member : group.getMembers()) {
                            System.out.println("\t\t" + member.getUsername() + " (" + member.getRole().name() + ")");
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
                    .doOnNext(group -> System.out.println("group: " + group.getGroupId()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limit != null) {
            this.service.group().listGroups(limit, cursor)
                    .doOnNext(rsp -> {
                        List<EMGroup> groups = rsp.getEMGroups();
                        for (int i = 0; i < groups.size(); i++) {
                            System.out.println("group: " + groups.get(i).getGroupId());
                        }
                        System.out.println("cursor: " + rsp.getCursor());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().listAllGroups()
                    .doOnNext(group -> {
                        System.out.println("group: " + group.getGroupId());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    @Command(name = "user-setting", description = "Get notification setting for the user.")
    public void userSetting(@Parameters(index = "0", description = "username") String username) {
        System.out.println("username : " + username);
        this.service.notification()
                .getUserSetting(username)
                .doOnSuccess(settings -> {
                    System.out.println("username: " + settings.getUsername());
                    System.out.println("nickname: " + settings.getNickname());
                    System.out.println("showMessageContent: " + settings.getShowMessageContent());
                }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }
}
