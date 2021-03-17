package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import reactor.core.publisher.Mono;

@Component
@Command(name = "update", description = "Update a resource.")
public class UpdateCmd {

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

    @Command(name = "group", description = "Update a group's settings.")
    public void group(@CommandLine.Parameters(index = "0", description = "the group's id") String groupId,
                      @CommandLine.Option(names = {"--owner"}, description = "the new owner's username, who must be member of this group") String owner,
                      @CommandLine.Option(names = {"--announcement"}, description = "the announcement") String announcement,
                      @CommandLine.Option(names = {"--max-members"}, description = "the max number of members") Integer maxMembers,
                      @CommandLine.Option(names = {"--need-approve-to-join"}, description = "need approve to join") Boolean needApproveToJoin,
                      @CommandLine.Option(names = {"--can-member-invite"}, description = "can member invite others to join") Boolean canMemberInvite) {

        if (maxMembers != null || needApproveToJoin != null || canMemberInvite != null) {
            this.service.group().updateSettings(groupId, settings -> {
                if (maxMembers != null) {
                    settings.setMaxMembers(maxMembers);
                }
                if (needApproveToJoin != null) {
                    settings.setNeedApproveToJoin(needApproveToJoin);
                }
                if (canMemberInvite != null) {
                    settings.setCanMemberInviteOthers(canMemberInvite);
                }
            }).doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (owner != null) {
            this.service.group().updateGroupOwner(groupId, owner)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
        if (announcement != null) {
            this.service.group().updateGroupAnnouncement(groupId, announcement)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    @Command(name = "user-setting", description = "Update notification setting for the user.")
    public void userSetting(@CommandLine.Parameters(index = "0") String username,
                       @CommandLine.Option(names = {"--nickname", "-n"}, description = "the nickname user see when receiving notification") String nickname,
                       @CommandLine.Option(names = {"--show-message-content", "-s"}, description = "show message content in the notification") Boolean showMessageContent) {
        this.service.notification()
                .updateUserSetting(username, settings -> {
                    if (StringUtils.hasText(nickname)) {
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
}
