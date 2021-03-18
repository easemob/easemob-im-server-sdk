package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.Command;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "delete", description = "Delete a resource.")
public class DeleteCmd {

    @Autowired
    private EMService service;

    @Command(name = "block", description = "Unblock user from resource.")
    public void block(@Parameters(index = "0", description = "user to unblock") String username,
                      @Option(names = {"--msg-to-user"}, description = "unblock user send message to this user") String msgToUsername,
                      @Option(names = {"--msg-to-group"}, description = "unblock user send message to this group") String msgToGroupId,
                      @Option(names = {"--msg-to-room"}, description = "unblock user send message to this room") String msgToRoomId,
                      @Option(names = {"--login"}, description = "unblock user to login") boolean login,
                      @Option(names = {"--join-group"}, description = "unblock user to join group") String groupId) {
        if (StringUtils.hasText(msgToUsername)) {
            this.service.block().unblockUserSendMsgToUser(username, msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (StringUtils.hasText(msgToGroupId)) {
            this.service.block().unblockUserSendMsgToGroup(username, msgToGroupId)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .block();
        }
        if (StringUtils.hasText(msgToRoomId)) {
            // TODO: implement unblock users send msg to room
            System.out.println("Not implemented");
        }
        if (login) {
            this.service.block().unblockUserLogin(username)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block(Duration.ofSeconds(3));
        }
        if (StringUtils.hasText(groupId)) {
            this.service.block().unblockUserJoinGroup(username, groupId)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .block();
        }
    }

    @Command(name = "user", description = "Delete a user.", mixinStandardHelpOptions = true)
    public void user(@Parameters(index = "0", paramLabel = "username", description = "user to unblock", defaultValue = "") String username,
                     @Option(names = {"--all"}, description = "delete all users") boolean all) {
        if (all) {
            this.service.user().deleteAll()
                    .doOnNext(user -> System.out.println("user " + user + " deleted"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (StringUtils.hasText(username)) {
            this.service.user().delete(username)
                    .doOnSuccess(user -> System.out.println("user " + user + " deleted"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            System.out.println("must specify one username or use --all");
        }
    }

    @Command(name = "contact", description = "Remove a contact from the user.")
    public void contact(@Parameters(index = "0", description = "the user's username") String user,
                        @Parameters(index = "1", description = "the contact's username") String contact) {
        this.service.contact().remove(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "group", description = "Delete a group.\n" +
            "Messages will be destroyed with the group, while the chat history is reserved.")
    public void group(@Parameters(index = "0", description = "the group's id") String groupId) {
        this.service.group().destroyGroup(groupId)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "session", description = "Force user logout.")
    public void session(@Parameters(index = "0", description = "the username") String username,
                        @Parameters(index = "1", description = "the device name", defaultValue = "") String deviceName,
                        @Option(names = "--all", description = "force logout all devices") boolean all) {
        if (all) {
            this.service.user().forceLogoutAllDevices(username)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (StringUtils.hasText(deviceName)) {
            this.service.user().forceLogoutOneDevice(username, deviceName)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            System.out.println("must specify one device or use --all");
        }
    }
}
