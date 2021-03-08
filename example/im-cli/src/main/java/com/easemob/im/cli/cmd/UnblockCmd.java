package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.time.Duration;
import java.util.List;

@Component
@Command(name = "unblock", description = "Block users from sending message or login.")
public class UnblockCmd {
    @Autowired
    private EMService service;

    @Command(name = "msg", description = "Unblock users from sending message to specified user, group or room")
    public void unblockUsersFromSendMsg(@Option(names = {"--from-user"}, description = "from this user") List<String> unblockUsers,
                                        @Option(names = {"--to-user"}, description = "to this user") String toUser,
                                        @Option(names = {"--to-group"}, description = "to this group") String toGroup,
                                        @Option(names = {"--to-room"}, description = "to this room") String toRoom) {
        if (toUser != null) {
            this.service.block().unblockUsersSendMsgToUser(unblockUsers, toUser)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .block();
        } else if (toGroup != null) {
            this.service.block().unblockUserSendMsgToGroup(unblockUsers.get(0), toGroup)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .block();
        } else if (toRoom != null) {
            // TODO: implement block users send msg to room
            System.out.println("Not implemented");
        } else {
            System.out.println("At least one of --to-user,-u/--to-group,-g/--to-room,-r must be specified.");
        }

    }

    @Command(name = "login", description = "UnBlock user from login.")
    public void unblockUserLogin(@Parameters(index = "0", description = "the user to unblock") String username) {
        this.service.block().unblockUserLogin(username)
                .doOnSuccess(ignored -> System.out.println("done"))
                .block(Duration.ofSeconds(3));
    }

    @Command(name = "join", description = "UnBlock user join group.")
    public void unblockUserJoinGroup(@Parameters(index = "0", description = "the group") String group,
                                   @Parameters(index = "1", description = "the username") String username) {
        this.service.block().unblockUserJoinGroup(username, group)
                .doOnSuccess(ignored -> System.out.println("done"))
                .block();
    }

}
