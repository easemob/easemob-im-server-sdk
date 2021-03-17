package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
@Command(name = "delete", description = "Delete a resource.")
public class DeleteCmd implements Action {

    @Autowired
    private EMService service;

    @Command(name = "block", description = "Unblock user from resource.")
    public void block(@CommandLine.Parameters(index = "0", description = "user to unblock") String username,
                      @CommandLine.Option(names = {"--msg-to-user"}, description = "unblock user send message to this user") String msgToUsername,
                      @CommandLine.Option(names = {"--msg-to-group"}, description = "unblock user send message to this group") String msgToGroupId,
                      @CommandLine.Option(names = {"--msg-to-room"}, description = "unblock user send message to this room") String msgToRoomId,
                      @CommandLine.Option(names = {"--login"}, description = "unblock user to login") boolean login,
                      @CommandLine.Option(names = {"--join-group"}, description = "unblock user to join group") String groupId) {
        if (hasText(msgToUsername)) {
            this.service.block().unblockUsersSendMsgToUser(Arrays.asList(username), msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (hasText(msgToGroupId)) {
            this.service.block().unblockUserSendMsgToGroup(username, msgToGroupId)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .block();
        }
        if (hasText(msgToRoomId)) {
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
        if (hasText(groupId)) {
            this.service.block().unblockUserJoinGroup(username, groupId)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .block();
        }
    }

    @Command(name = "user", description = "Delete a user.", mixinStandardHelpOptions = true)
    public void user(@CommandLine.Parameters(index = "0", description = "the username", defaultValue = "") String username,
                       @CommandLine.Option(names = {"--all"}, description = "delete all users") boolean all) {
        if (all) {
            this.service.user().deleteAll()
                    .doOnNext(user -> System.out.println("user " + user.getUsername() + " deleted"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else {
            this.service.user().delete(username)
                    .doOnSuccess(user -> System.out.println("user " + user.getUsername() + " deleted"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    @Command(name = "contact", description = "Remove a contact from the user.")
    public void contact(@CommandLine.Parameters(index = "0", description = "the user's username") String user,
                              @CommandLine.Parameters(index = "1", description = "the contact's username") String contact) {
        this.service.contact().remove(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }
}
