package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Command(name = "group", description = "Group related commands.")
public class GroupCmd {

    @Autowired
    private EMService service;

    // TODO: add api to list public groups
    @Command(name = "list", description = "List groups.", subcommands = {CreateGroupCmd.class})
    public void listGroups(@Option(names = {"--joined-by-user"}, description = "the user's username, who have joined this group") String joinedByUser,
                           @Option(names = {"--limit"}, description = "the limit") Integer limit,
                           @Option(names = {"--cursor"}, description = "the cursor") String cursor) {
        if (joinedByUser != null) {
            this.service.group().listGroupsUserJoined(joinedByUser)
                    .doOnNext(group -> System.out.println("group: " + group.getGroupId()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        } else if (limit != null) {
            this.service.group().listGroups(limit, cursor)
                    .doOnNext(rsp -> {
                        List<EMGroup> groups = rsp.getEMGroups();
                        for (int i = 0; i < groups.size(); i++) {
                            System.out.println("group: " + groups.get(i).getGroupId());
                        }
                        System.out.println("cursor: " + rsp.getCursor());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else {
            this.service.group().listAllGroups()
                    .doOnNext(group -> {
                        System.out.println("group: " + group.getGroupId());
                    })
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .blockLast();
        }
    }

    @Command(name = "create", description = "Create a group.")
    public static class CreateGroupCmd {

        @Autowired
        private EMService service;

        @Command(name = "public", description = "Create a public group. \n" +
                "Public groups can be listed using Android, iOS, Web SDKs. So that end user can discover and join public groups.")
        public void createPublicGroup(@Option(names = "--owner", required = true, description = "the owner's username") String owner,
                                      @Option(names = "--member", description = "the member's username") List<String> members,
                                      @Option(names = "--max-members", defaultValue = "200", description = "max number of members") int maxMembers,
                                      @Option(names = "--need-approve-to-join", defaultValue = "false", description = "need approve to join") boolean needApproveToJoin) {
            this.service.group().createPublicGroup(owner, members, maxMembers, needApproveToJoin)
                    .doOnSuccess(group -> System.out.println("group: " + group.getGroupId()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }

        @Command(name = "private", description = "Create a private group.\n" +
                "Private groups can NOT be listed using Android, iOS, Web SDKs.")
        public void createPrivateGroup(@Option(names = "--owner", required = true, description = "the owner's username") String owner,
                                       @Option(names = "--member", description = "the member's username") List<String> members,
                                       @Option(names = "--max-members", defaultValue = "200", description = "max number of members") int maxMembers,
                                       @Option(names = "--can-member-invite", defaultValue = "false", description = "can member invite others to join") boolean canMemberInvite) {
            this.service.group().createPrivateGroup(owner, members, maxMembers, canMemberInvite)
                    .doOnSuccess(group -> System.out.println(group.getGroupId()))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

    @Command(name = "destroy", description = "Destroy a group.\n" +
            "Messages will be destroyed with the group, while the chat history is reserved.")
    public void destroyGroup(@Parameters(index = "0", description = "the group's id") String groupId) {
        this.service.group().destroyGroup(groupId)
                .doOnSuccess(ignored -> System.out.println("done"))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();
    }

    public static class GetGroupOptions {
        @Option(names = {"--settings"}, description = "get settings") boolean settings;
        @Option(names = {"--owner"}, description = "get owner") boolean owner;
        @Option(names = {"--members"}, description = "get members") boolean members;
        @Option(names = {"--announcement"}, description = "get announcement") boolean announcement;
    }

    @Command(name = "get", description = "Get a group's info.")
    public void getGroupInfo(@Parameters(index = "0", description = "the group's id") String groupId,
                             @ArgGroup GetGroupOptions options) {
        this.service.group().getGroupDetails(groupId)
                .doOnSuccess(group -> {
                    System.out.println("group: " + group.getGroupId());
                    if (options == null || options.settings) {
                        System.out.println("\tisPublic: " + group.getIsPublic());
                        System.out.println("\tmaxMembers: " + group.getMaxMembers());
                        System.out.println("\tneedApproveToJoin: " + group.getNeedApproveToJoin());
                        System.out.println("\tcanMemberInviteOthers: " + group.getCanMemberInviteOthers());
                    }
                    if (options == null || options.owner) {
                        System.out.println("\towner: " + group.getOwner());
                    }
                    if (options == null || options.members) {
                        System.out.println("\tmembers: [");
                        for (EMGroupMember member : group.getMembers()) {
                            System.out.println("\t\t" + member.getUsername() + " (" + member.getRole().name() + ")");
                        }
                        System.out.println("\t]");
                    }
                })
                .then(Mono.defer(() -> {
                    if (options == null || options.announcement) {
                        return this.service.group().getGroupAnnouncement(groupId)
                                .doOnSuccess(announcement -> System.out.println("announcement: " + announcement));
                    }
                    return Mono.empty();
                }))
                .doOnError(err -> System.out.println("error: " + err.getMessage()))
                .onErrorResume(EMException.class, ignore -> Mono.empty())
                .block();


    }

    public static class UpdateGroupOptions {
        @ArgGroup(exclusive = false) UpdateGroupSettingOptions settings;
        @Option(names = {"--owner"}, description = "the new owner's username, who must be member of this group") String owner;
        @Option(names = {"--announcement"}, description = "the announcement") String announcement;
    }

    public static class UpdateGroupSettingOptions {
        @Option(names = {"--max-members"}, description = "the max number of members") Integer maxMembers;
        @Option(names = {"--need-approve-to-join"}, description = "need approve to join") Boolean needApproveToJoin;
        @Option(names = {"--can-member-invite"}, description = "can member invite others to join") Boolean canMemberInvite;
    }

    @Command(name = "update", description = "Update a group's settings.")
    public void updateGroup(@Parameters(index = "0", description = "the group's id") String groupId,
                            @ArgGroup(multiplicity = "1") UpdateGroupOptions options) {

        if (options.settings != null) {
            this.service.group().updateSettings(groupId, settings -> {
                if (options.settings.maxMembers != null) {
                    settings.setMaxMembers(options.settings.maxMembers);
                }
                if (options.settings.needApproveToJoin != null) {
                    settings.setNeedApproveToJoin(options.settings.needApproveToJoin);
                }
                if (options.settings.canMemberInvite != null) {
                    settings.setCanMemberInviteOthers(options.settings.canMemberInvite);
                }
            }).doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (options.owner != null) {
            this.service.group().updateGroupOwner(groupId, options.owner)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        } else if (options.announcement != null) {
            this.service.group().updateGroupAnnouncement(groupId, options.announcement)
                    .doOnSuccess(ignored -> System.out.println("done"))
                    .doOnError(err -> System.out.println("error: " + err.getMessage()))
                    .onErrorResume(EMException.class, ignore -> Mono.empty())
                    .block();
        }
    }

}
