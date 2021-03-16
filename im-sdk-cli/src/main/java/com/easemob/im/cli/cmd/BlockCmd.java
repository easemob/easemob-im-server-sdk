package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;



@Component
@Command(name = "block", description = "Block users from sending message or login.")
public class BlockCmd {

    @Autowired
    private EMService service;

    @Command(name = "msg", description = "Block users from sending msg to specified user, group or room.")
    public void blockUsersFromSendMsg(@Option(names = {"--from-user", "-u"}, description = "users to block") List<String> blockUsers,
                                      @Option(names = {"--to-user", "-U"}, description = "to this user") String toUser,
                                      @Option(names = {"--to-group", "-G"}, description = "to this group") String toGroup,
                                      @Option(names = {"--to-room", "-R"}, description = "to this room") String toRoom) {
        if (toUser != null) {
            this.service.block().blockUsersSendMsgToUser(blockUsers, toUser)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (toGroup != null) {
            blockUsers.forEach(user -> {
                this.service.block().blockUserSendMsgToGroup(user, toGroup, Duration.ofMillis(6000))
                        .doOnSuccess(ignore -> System.out.println("done"))
                        .block();
            });
        } else if (toRoom != null) {
            // TODO: implement block users send msg to room
            System.out.println("Not implemented");
        } else {
            System.out.println("At least one of --to-user,-u/--to-group,-g/--to-room,-r must be specified.");
        }
    }

    @Command(name = "login", description = "Block user from login.")
    public void blockUserLogin(@Parameters(index = "0", description = "the user to block") String username) {
        this.service.block().blockUserLogin(username)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block(Duration.ofSeconds(3));
    }

    @Command(name = "join", description = "Block user join group.")
    public void blockUserJoinGroup(@Option(names = "--from-user", description = "the username") String username,
                                   @Option(names = "--join-group", description = "the group") String group) {
        this.service.block().blockUserJoinGroup(username, group)
                .doOnSuccess(ignored -> System.out.println("done"))
                .block();
    }

}
