package com.easemob.im.server.api.group;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMBlock;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMPage;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.easemob.im.server.utils.RandomMaker.makeRandomUserName;

public class GroupIT extends AbstractIT {

    GroupIT() {
        super();
    }

    @Test
    void testGroupCreatePublic() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 10, null)
                        .block(Duration.ofSeconds(10)));
        List<String> groupMembers = groupMemberPage.getValues();
        if (groupMembers.size() != members.size()) {
            throw new RuntimeException(
                    String.format("incorrect number of group %s members", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupCreatePrivate() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 100, null).block(Duration.ofSeconds(10)));
        List<String> groupMembers = groupMemberPage.getValues();
        if (groupMembers.size() != members.size()) {
            throw new RuntimeException(
                    String.format("incorrect number of group %s members", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupDestroy() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertThrows(EMNotFoundException.class,
                () -> this.service.group().getGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupListAllGroups() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().listAllGroups().blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupListGroups() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().listGroups(1, null).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupGet() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().getGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupUpdate() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        int maxMembers = 100;
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group()
                .updateGroup(groupId, settings -> settings.setMaxMembers(maxMembers))
                .block(Duration.ofSeconds(10)));
        EMGroup group = assertDoesNotThrow(
                () -> this.service.group().getGroup(groupId).block(Duration.ofSeconds(10)));
        if (group.getMaxMembers() != maxMembers) {
            throw new RuntimeException(String.format("%s group max member update fail", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupUpdateOwner() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().updateGroupOwner(groupId, randomMemberUsername)
                        .block(Duration.ofSeconds(10)));
        EMGroup group = assertDoesNotThrow(
                () -> this.service.group().getGroup(groupId).block(Duration.ofSeconds(10)));
        if (!group.getOwner().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s group update owner %s fail", groupId, randomMemberUsername));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupGetAnnouncement() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group().getGroupAnnouncement(groupId)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupUpdateAnnouncement() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String updateAnnouncement = "update announcement";
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().updateGroupAnnouncement(groupId, updateAnnouncement)
                        .block(Duration.ofSeconds(10)));
        String newAnnouncement = assertDoesNotThrow(
                () -> this.service.group().getGroupAnnouncement(groupId)
                        .block(Duration.ofSeconds(10)));
        if (!updateAnnouncement.equals(newAnnouncement)) {
            throw new RuntimeException(String.format("update %s group announcement fail", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupListAllGroupMembers() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group().listAllGroupMembers(groupId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupListMembers() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Duration.ofSeconds(10)));
        List<String> groupMembers = groupMemberPage.getValues();
        if (!groupMembers.isEmpty()) {
            groupMembers.forEach(member -> {
                if (!member.equals(randomMemberUsername)) {
                    throw new RuntimeException(
                            String.format("%s does not exist in %s group member list", member,
                                    groupId));
                }
            });
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupAddMember() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group().addGroupMember(groupId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Duration.ofSeconds(10)));
        List<String> groupMembers = groupMemberPage.getValues();
        if (!groupMembers.isEmpty()) {
            groupMembers.forEach(member -> {
                if (!member.equals(randomMemberUsername)) {
                    throw new RuntimeException(
                            String.format("%s does not exist in %s group member list", member,
                                    groupId));
                }
            });
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupRemoveMember() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.group().removeGroupMember(groupId, randomMemberUsername)
                        .block(Duration.ofSeconds(10)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Duration.ofSeconds(10)));
        List<String> groupMembers = groupMemberPage.getValues();
        if (!groupMembers.isEmpty()) {
            throw new RuntimeException(
                    String.format("%s exist in %s group member list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupAddAdmin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomAdminUsername = makeRandomUserName();
        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomAdminUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomAdminUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group().addGroupAdmin(groupId, randomAdminUsername)
                .block(Duration.ofSeconds(10)));
        String adminName = assertDoesNotThrow(() -> this.service.group().listGroupAdmins(groupId)
                .blockFirst(Duration.ofSeconds(3)));
        if (!adminName.equals(randomAdminUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group admin list", randomAdminUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomAdminUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupRemoveAdmin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomAdminUsername = makeRandomUserName();
        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomAdminUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomAdminUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group().addGroupAdmin(groupId, randomAdminUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.group().removeGroupAdmin(groupId, randomAdminUsername)
                .block(Duration.ofSeconds(10)));
        String adminName = assertDoesNotThrow(() -> this.service.group().listGroupAdmins(groupId)
                .blockFirst(Duration.ofSeconds(3)));
        if (adminName != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group admin list", randomAdminUsername, groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomAdminUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupGetUsersBlockedJoin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.block().blockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (block != null && !block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group block list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupBlockUserJoin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.block().blockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Duration.ofSeconds(10)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Duration.ofSeconds(10)));
        if (!groupMemberPage.getValues().isEmpty()) {
            groupMemberPage.getValues().forEach(member -> {
                if (member.equals(randomMemberUsername)) {
                    throw new RuntimeException(
                            String.format("%s exist in %s group member list", member, groupId));
                }
            });
        }
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (block != null && !block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group block list", randomMemberUsername,
                            groupId));
        }

        assertDoesNotThrow(() -> this.service.group().addGroupMember(groupId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        EMBlock block1 = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (block1 != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group block list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupUnblockUserJoin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.block().blockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Duration.ofSeconds(10)));
        if (block != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group block list", randomMemberUsername,
                            groupId));
        }

        List<String> groupMembers = groupMemberPage.getValues();
        if (!groupMembers.isEmpty()) {
            groupMembers.forEach(member -> {
                if (member.equals(randomMemberUsername)) {
                    throw new RuntimeException(
                            String.format("%s exist in %s group member list", member, groupId));
                }
            });
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupGetUsersBlockedSendMsg() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToGroup(randomMemberUsername, groupId, Duration.ofMillis(6000))
                .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedSendMsgToGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (block == null) {
            throw new RuntimeException(
                    String.format("block %s send message to %s group fail", randomMemberUsername,
                            groupId));
        }

        if (block != null && !block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group mute list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupBlockUserSendMsg() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToGroup(randomMemberUsername, groupId, Duration.ofMillis(3000))
                .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedSendMsgToGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (block == null) {
            throw new RuntimeException(
                    String.format("block %s send message to %s group fail", randomMemberUsername,
                            groupId));
        }

        if (block != null && !block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group mute list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testGroupUnblockUserSendMsg() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToGroup(randomMemberUsername, groupId, Duration.ofMillis(6000))
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserSendMsgToGroup(randomMemberUsername, groupId)
                        .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedSendMsgToGroup(groupId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (block != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group mute list", randomMemberUsername, groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

}
