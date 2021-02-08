package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import static picocli.CommandLine.Option.NULL_VALUE;

@Component
@Command(name = "user", description = "User commands.")
public class UserCmd {
    @Autowired
    private EMService service;

    @Command(name = "create", description = "Create a user.", mixinStandardHelpOptions = true)
    public void create(@Parameters(index = "0", description = "the username") String username,
                         @Parameters(index = "1", description = "the password") String password) {
        service.user().create(username, password)
                .doOnSuccess(user -> System.out.println(user.getUsername() + " created"))
                .block();
    }

    @Command(name = "delete", description = "Delete a user.", mixinStandardHelpOptions = true)
    public void delete(@Parameters(index = "0", description = "the username") String username) {
        this.service.user().delete(username)
            .doOnSuccess(user -> System.out.println(user.getUsername() + " deleted"))
            .block();
    }

    @Command(name = "reset-password", description = "Reset password for the user.", mixinStandardHelpOptions = true)
    public void resetPassword(@Parameters(index = "0", description = "the username") String username,
                              @Parameters(index = "1", description = "the password") String password) {
        this.service.user().resetPassword(username, password)
                .doOnSuccess(ignore -> System.out.println("done"))
                .block();
    }



    @Command(name = "get", description = "Get user info.", mixinStandardHelpOptions = true)
    public void get(@Parameters(index = "0", description = "The username") String username) {
        this.service.user().get(username)
                .doOnNext(user -> {
                    System.out.println("user: " + user.getUsername());
                    System.out.println("canLogin: " + user.getCanLogin());
                }).block();
    }

    @Command(name = "list", description = "List users. By default all users are listed.\nUse --limit and --cursor to control ", mixinStandardHelpOptions = true)
    public void list(@Option(names = {"--blocked-by-user"}, description = "to list users blocked from sending message to this user") String blockedByUser,
                     @Option(names = {"--limit"}, description = "to limit") Integer limit,
                     @Option(names = {"--cursor"}, description = "the cursor") String cursor) {

        if (blockedByUser != null) {
            service.block().getUsersBlockedFromSendMsgToUser(blockedByUser)
                    .doOnNext(username -> System.out.println("user: "+username))
                    .doOnSubscribe(s -> System.out.println("blocked by user: " + blockedByUser))
                    .blockLast();
        } else if (limit != null) {
            service.user().listUsers(limit, cursor)
                    .doOnNext(rsp -> {
                        System.out.println("cursor: " + rsp.getCursor());
                        System.out.println("users:");
                        for (EMUser user : rsp.getEMUsers()) {
                            System.out.println("\t"+user.getUsername());
                        }
                    })
                    .doOnSubscribe(s -> System.out.println("limit: " + limit + "\ncursor: " + cursor))
                    .block();
        } else {
            service.user().listAllUsers()
                    .doOnNext(user -> {
                        System.out.println(user.getUsername());
                    })
                    .blockLast();
        }
    }

}
