package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import reactor.core.publisher.Mono;

@Component
@Command(name = "update")
public class UpdateCmd implements Action{

    @Autowired
    private EMService service;

    @Command(name = "password", description = "Reset password for the user.", mixinStandardHelpOptions = true)
    public void password(@CommandLine.Parameters(index = "0", description = "the username") String username,
                             @CommandLine.Parameters(index = "1", description = "the password") String password) {
        this.service.user().resetPassword(username, password)
                .doOnSuccess(ignore -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }
}
