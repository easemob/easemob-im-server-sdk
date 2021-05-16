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

import java.util.Map;

@Component
@Command(name = "update", description = "Update a resource.", mixinStandardHelpOptions = true)
public class UpdateCmd {

    @Autowired
    private EMService service;

    @Command(name = "user", description = "Update user info.", mixinStandardHelpOptions = true)
    public void user(@Parameters(description = "the username") String username,
                         @Option(names = "--password", description = "update user password") String password) {
        if (password != null) {
            this.service.user().updateUserPassword(username, password)
                    .doOnSuccess(ignore -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    private static class GroupArgGroup {
        @Option(names = {"--owner"}, description = "the new owner's username, who must be member of this group")
        String owner;

        @Option(names = "--name", description = "the group's name")
        String name;

        @Option(names = "--description", description = "the group's description")
        String description;

        @Option(names = {"--announcement"}, description = "the announcement")
        String announcement;

        @Option(names = {"--max-members"}, description = "the max number of members")
        Integer maxMembers;

        @Option(names = {"--can-member-invite"}, description = "can member invite others to join")
        Boolean canMemberInvite;
    }

    @Command(name = "group", description = "Update a group's settings.", mixinStandardHelpOptions = true)
    public void group(@Parameters(description = "the group's id") String groupId,
                      @ArgGroup(multiplicity = "1", exclusive = false) GroupArgGroup argGroup) {
        this.service.group().updateGroup(groupId, request -> {
            request.setName(argGroup.name)
                    .setDescription(argGroup.description)
                    .setMaxMembers(argGroup.maxMembers)
                    .setCanMemberInviteOthers(argGroup.canMemberInvite)
                    .setNeedApproveToJoin(argGroup.canMemberInvite == null ? null : !argGroup.canMemberInvite);
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

    @Command(name = "room", description = "Update a room's settings.", mixinStandardHelpOptions = true)
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

    @Command(name = "metadata", description = "Update user metadata.", mixinStandardHelpOptions = true)
    public void metadata(@Parameters(description = "the username") String username,
                     @Option(names = "--data", description = "set user metadata") Map<String, String> dataMap) {
        this.service.metadata().setUser(username, dataMap)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

}
