package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
public class CreateCmd implements Action {

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
        if (hasText(msgToUsername)) {
            this.service.block().blockUsersSendMsgToUser(Arrays.asList(username), msgToUsername)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (hasText(msgToGroupId)) {
            this.service.block().blockUserSendMsgToGroup(username, msgToGroupId, Duration.ofMillis(6000))
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .block();
        }
        if (hasText(msgToRoomId)) {
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

        if (hasText(groupId)) {
            this.service.block().blockUserJoinGroup(username, groupId)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .block();
        }
    }
}
