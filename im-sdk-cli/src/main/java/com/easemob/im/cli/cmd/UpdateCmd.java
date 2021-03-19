package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

@Component
@Command(name = "update", description = "Update a resource.")
public class UpdateCmd {

    @Autowired
    private EMService service;

    @Command(name = "password", description = "Reset password for the user.", mixinStandardHelpOptions = true)
    public void password(@Parameters(description = "the username") String username,
                         @Parameters(description = "the password") String password) {
        this.service.user().updateUserPassword(username, password)
                .doOnSuccess(ignore -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    private static class GroupArgGroup {
        @Option(names = {"--owner"}, description = "the new owner's username, who must be member of this group")
        String owner;

        @Option(names = {"--announcement"}, description = "the announcement")
        String announcement;

        @Option(names = {"--max-members"}, description = "the max number of members")
        Integer maxMembers;

        @Option(names = {"--can-member-invite"}, description = "can member invite others to join")
        Boolean canMemberInvite;
    }

    @Command(name = "group", description = "Update a group's settings.")
    public void group(@Parameters(description = "the group's id") String groupId,
                      @ArgGroup(multiplicity = "1", exclusive = false) GroupArgGroup argGroup) {
        this.service.group().updateGroup(groupId, request -> {
            if (argGroup.maxMembers != null) {
                request.setMaxMembers(argGroup.maxMembers);
            }
            if (argGroup.canMemberInvite != null) {
                request.setCanMemberInviteOthers(argGroup.canMemberInvite);
                request.setNeedApproveToJoin(!argGroup.canMemberInvite);
            }
        }).doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();

        if (StringUtils.hasText(argGroup.owner)) {
            this.service.group().updateGroupOwner(groupId, argGroup.owner)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }

        if (argGroup.announcement != null) {
            this.service.group().updateGroupAnnouncement(groupId, argGroup.announcement)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class RoomArgGroup {
        @Option(names = "--name", description = "new room name")
        String name;

        @Option(names = {"--description"}, description = "the announcement")
        String description;

        @Option(names = {"--max-members"}, description = "the max number of members")
        Integer maxMembers;
    }

    @Command(name = "room", description = "Update a room's settings.")
    public void room(@Parameters(description = "the room's id") String roomId,
                     @ArgGroup(multiplicity = "1", exclusive = false) RoomArgGroup argGroup) {
        this.service.room().updateRoom(roomId, request -> {
            if (argGroup.name != null) {
                request.withName(argGroup.name);
            }
            if (argGroup.description != null) {
                request.withDescription(argGroup.description);
            }
            if (argGroup.maxMembers != null) {
                request.withMaxMembers(argGroup.maxMembers);
            }
        }).doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }
}
