package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

@Component
@Command(name = "notification", description = "Notification commands.")
public class NotificationCmd {

    @Autowired
    private EMService service;

    @Command(name = "update-user-setting", description = "Update notification setting for the user.")
    public void update(@Parameters(index = "0") String username,
                       @Option(names = {"--nickname", "-n"}, description = "the nickname user see when receiving notification") String nickname,
                       @Option(names = {"--show-message-content", "-s"}, description = "show message content in the notification") Boolean showMessageContent) {

        this.service.notification()
                .updateUserSetting(username, settings -> {
                    if (nickname != null) {
                        System.out.println("Setting nickname to "+ nickname);
                        settings.withNickname(nickname);
                    }
                    if (showMessageContent != null) {
                        System.out.println("Setting showMessageContent to "+ showMessageContent);
                        settings.withShowMessageContent(showMessageContent);
                    }
                })
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    @Command(name = "get-user-setting", description = "Get notification setting for the user.")
    public void get(@Parameters(index = "0") String username) {
        this.service.notification()
                .getUserSetting(username)
                .doOnSuccess(settings -> {
                    System.out.println("username: "+settings.getUsername());
                    System.out.println("nickname: "+settings.getNickname());
                    System.out.println("showMessageContent: "+settings.getShowMessageContent());
                }).doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }
}
