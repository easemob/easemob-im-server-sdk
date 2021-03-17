package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
@Command(name = "create", description = "Create a resource.")
public class CreateCmd {

    @Autowired
    private EMService service;

    @Command(name = "attachment", description = "Create an attachment.")
    public void createAttachment(@Option(names = "-f", description = "from file") Path file) {
        this.service.file().uploadFile(file, false)
                .doOnNext(id -> System.out.println(String.format("id: %s", id)))
                .doOnError(err -> System.out.println(String.format("error: %s", err.getMessage())))
                .onErrorResume(EMException.class, err -> Mono.empty())
                .block();
    }

    @Command(name = "block", description = "Block user from resource.")
    public void block(@CommandLine.Parameters(index = "0", paramLabel = "userId", description = "user to block") String username,
                      @CommandLine.Option(names = {"--msg-to-user"}, description = "block user send message to this user") String msgToUsername,
                      @CommandLine.Option(names = {"--msg-to-group"}, description = "block user send message to this group") String msgToGroupId,
                      @CommandLine.Option(names = {"--msg-to-room"}, description = "block user send message to this room") String msgToRoomId,
                      @CommandLine.Option(names = {"--login"}, description = "block user to login") boolean login,
                      @CommandLine.Option(names = {"--join-group"}, description = "block user to join group") String groupId) {
        if (StringUtils.hasText(msgToUsername)) {
            this.service.block().blockUsersSendMsgToUser(Arrays.asList(username), msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(msgToGroupId)) {
            this.service.block().blockUserSendMsgToGroup(username, msgToGroupId, Duration.ofMillis(6000))
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .block();
        }
        if (StringUtils.hasText(msgToRoomId)) {
            // TODO: implement unblock users send msg to room
            System.out.println("Not implemented");
        }
        if (login) {
            this.service.block().blockUserLogin(username)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block(Duration.ofSeconds(3));
        }
        if (StringUtils.hasText(groupId)) {
            this.service.block().blockUserJoinGroup(username, groupId)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .block();
        }
    }

    @Command(name = "user", description = "Create a user.", mixinStandardHelpOptions = true)
    public void user(@CommandLine.Parameters(index = "0", description = "the username") String username,
                     @CommandLine.Parameters(index = "1", description = "the password") String password) {
        service.user().create(username, password)
                .doOnSuccess(user -> System.out.println(user.getUsername() + " created"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "contact", description = "Add a contact to the user.")
    public void contact(@CommandLine.Parameters(index = "0", description = "the user's username") String user,
                        @CommandLine.Parameters(index = "1", description = "the contact's username") String contact) {
        this.service.contact().add(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "group", description = "Create a group.\n" +
            "Public(by default) groups can be listed using Android, iOS, Web SDKs. So that end user can discover and join public groups.\n" +
            "Private groups can NOT be listed using Android, iOS, Web SDKs.")
    public void group(@Option(names = "--private", description = "create a private group") boolean isPrivate,
                      @Option(names = "--owner", required = true, description = "the owner's username") String owner,
                      @Option(names = "--member", description = "the member's username") List<String> members,
                      @Option(names = "--max-members", defaultValue = "200", description = "max number of members") int maxMembers,
                      @Option(names = "--can-member-invite", defaultValue = "false", description = "can member invite others to join") boolean canMemberInvite,
                      @Option(names = "--need-approve-to-join", defaultValue = "false", description = "only for public group, whether need approve to join") boolean needApproveToJoin) {
        if (isPrivate) {
            this.service.group().createPrivateGroup(owner, members, maxMembers, canMemberInvite)
                    .doOnSuccess(group -> System.out.println(group.getGroupId()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().createPublicGroup(owner, members, maxMembers, needApproveToJoin)
                    .doOnSuccess(group -> System.out.println("group: " + group.getGroupId()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }
}
