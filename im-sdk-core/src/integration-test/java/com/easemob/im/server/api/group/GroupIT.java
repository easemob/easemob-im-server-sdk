package com.easemob.im.server.api.group;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMBlock;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMPage;
import com.easemob.im.server.model.EMRemoveMember;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupIT extends AbstractIT {

    GroupIT() {
        super();
    }

    @Test
    void testGroupCreatePublic() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 10, null)
                        .block(Utilities.IT_TIMEOUT));
        List<String> groupMembers = groupMemberPage.getValues();
        if (groupMembers.size() != members.size()) {
            throw new RuntimeException(
                    String.format("incorrect number of group %s members", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupCreatePublicWithCustom() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true, "custom").block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 10, null)
                        .block(Utilities.IT_TIMEOUT));
        List<String> groupMembers = groupMemberPage.getValues();
        if (groupMembers.size() != members.size()) {
            throw new RuntimeException(
                    String.format("incorrect number of group %s members", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupCreatePrivate() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 100, null)
                        .block(Utilities.IT_TIMEOUT));
        List<String> groupMembers = groupMemberPage.getValues();
        if (groupMembers.size() != members.size()) {
            throw new RuntimeException(
                    String.format("incorrect number of group %s members", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupDestroy() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertThrows(EMNotFoundException.class,
                () -> this.service.group().getGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupListAllGroups() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().listAllGroups().blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupListGroups() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().listGroups(1, null).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupGet() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        String randomAdminUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomAdminUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomAdminUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().addGroupAdmin(groupId, randomAdminUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> {
                    EMGroup emGroup = this.service.group().getGroup(groupId).block(Utilities.IT_TIMEOUT);
                    assertEquals(emGroup.getAffiliationsCount(), 3);
                    assertNotNull(emGroup.getAffiliations());
                    EMGroup.Affiliation affiliation = emGroup.getAffiliations();
                    assertEquals(affiliation.getMembers().length, 2);
                });
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomAdminUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupGetWithCustom() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        String randomAdminUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomAdminUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomAdminUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true, "custom").block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().addGroupAdmin(groupId, randomAdminUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> {
                    EMGroup emGroup = this.service.group().getGroup(groupId).block(Utilities.IT_TIMEOUT);
                    assertEquals(emGroup.getAffiliationsCount(), 3);
                    assertNotNull(emGroup.getAffiliations());
                    EMGroup.Affiliation affiliation = emGroup.getAffiliations();
                    assertEquals(affiliation.getMembers().length, 2);
                    assertEquals(emGroup.getIsMute(), false);
                    assertEquals(emGroup.getCustom(), "custom");
                });
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomAdminUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupUpdate() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        int maxMembers = 100;
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group()
                .updateGroup(groupId, settings -> settings.setMaxMembers(maxMembers))
                .block(Utilities.IT_TIMEOUT));
        EMGroup group = assertDoesNotThrow(
                () -> this.service.group().getGroup(groupId).block(Utilities.IT_TIMEOUT));
        if (group.getMaxMembers() != maxMembers) {
            throw new RuntimeException(String.format("%s group max member update fail", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupUpdateOwner() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().updateGroupOwner(groupId, randomMemberUsername)
                        .block(Utilities.IT_TIMEOUT));
        EMGroup group = assertDoesNotThrow(
                () -> this.service.group().getGroup(groupId).block(Utilities.IT_TIMEOUT));
        if (!group.getOwner().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s group update owner %s fail", groupId, randomMemberUsername));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupGetAnnouncement() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group().getGroupAnnouncement(groupId)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupUpdateAnnouncement() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String updateAnnouncement = "update announcement";
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().updateGroupAnnouncement(groupId, updateAnnouncement)
                        .block(Utilities.IT_TIMEOUT));
        String newAnnouncement = assertDoesNotThrow(
                () -> this.service.group().getGroupAnnouncement(groupId)
                        .block(Utilities.IT_TIMEOUT));
        if (!updateAnnouncement.equals(newAnnouncement)) {
            throw new RuntimeException(String.format("update %s group announcement fail", groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupListAllGroupMembers() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group().listAllGroupMembers(groupId)
                .blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupListMembers() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Utilities.IT_TIMEOUT));
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
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupAddMemberSingle() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group().addGroupMember(groupId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Utilities.IT_TIMEOUT));
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
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupAddMemberBatch() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        String randomMemberUsername1 = Utilities.randomUserName();
        String randomMemberUsername2 = Utilities.randomUserName();

        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername2, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);

        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));

        List<String> addMembers = new ArrayList<>();
        addMembers.add(randomMemberUsername1);
        addMembers.add(randomMemberUsername2);

        assertDoesNotThrow(() -> this.service.group().addGroupMembers(groupId, addMembers)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername1)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername2)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupRemoveMemberSingle() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.group().removeGroupMember(groupId, randomMemberUsername)
                        .block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Utilities.IT_TIMEOUT));
        List<String> groupMembers = groupMemberPage.getValues();
        if (!groupMembers.isEmpty()) {
            throw new RuntimeException(
                    String.format("%s exist in %s group member list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupRemoveMemberBatch() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        String randomMemberUsername1 = Utilities.randomUserName();
        String randomMemberUsername2 = Utilities.randomUserName();

        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername2, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomMemberUsername1);
        members.add(randomMemberUsername2);

        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));


        List<String> removeMembers = new ArrayList<>();
        removeMembers.add(randomMemberUsername1);
        removeMembers.add(randomMemberUsername2);
        removeMembers.add("randomMemberUsername3");

        assertDoesNotThrow(
                () -> this.service.group().removeGroupMembers(groupId, removeMembers)
                        .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername1)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername2)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupAddAdmin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomAdminUsername = Utilities.randomUserName();
        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomAdminUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomAdminUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group().addGroupAdmin(groupId, randomAdminUsername)
                .block(Utilities.IT_TIMEOUT));
        String adminName = assertDoesNotThrow(() -> this.service.group().listGroupAdmins(groupId)
                .blockFirst(Utilities.IT_TIMEOUT));
        if (!adminName.equals(randomAdminUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group admin list", randomAdminUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomAdminUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupRemoveAdmin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomAdminUsername = Utilities.randomUserName();
        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        members.add(randomAdminUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomAdminUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group().addGroupAdmin(groupId, randomAdminUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.group().removeGroupAdmin(groupId, randomAdminUsername)
                .block(Utilities.IT_TIMEOUT));
        String adminName = assertDoesNotThrow(() -> this.service.group().listGroupAdmins(groupId)
                .blockFirst(Utilities.IT_TIMEOUT));
        if (adminName != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group admin list", randomAdminUsername, groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomAdminUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupGetUsersBlockedJoin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.block().blockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Utilities.IT_TIMEOUT));
        if (block != null && !block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group block list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupBlockUserJoin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.block().blockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Utilities.IT_TIMEOUT));
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
                        .blockFirst(Utilities.IT_TIMEOUT));
        if (block != null && !block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s group block list", randomMemberUsername,
                            groupId));
        }

        assertDoesNotThrow(() -> this.service.group().addGroupMember(groupId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        EMBlock block1 = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Utilities.IT_TIMEOUT));
        if (block1 != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group block list", randomMemberUsername,
                            groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupUnblockUserJoin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.block().blockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserJoinGroup(randomMemberUsername, groupId)
                        .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedJoinGroup(groupId)
                        .blockFirst(Utilities.IT_TIMEOUT));
        EMPage<String> groupMemberPage = assertDoesNotThrow(
                () -> this.service.group().listGroupMembers(groupId, 2, null)
                        .block(Utilities.IT_TIMEOUT));
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
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupGetUsersBlockedSendMsg() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToGroup(randomMemberUsername, groupId, Duration.ofMillis(6000))
                .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedSendMsgToGroup(groupId)
                        .blockFirst(Utilities.IT_TIMEOUT));
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
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupBlockUserSendMsg() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToGroup(randomMemberUsername, groupId, Duration.ofMillis(3000))
                .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedSendMsgToGroup(groupId)
                        .blockFirst(Utilities.IT_TIMEOUT));
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
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupUnblockUserSendMsg() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPrivateGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToGroup(randomMemberUsername, groupId, Duration.ofMillis(6000))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserSendMsgToGroup(randomMemberUsername, groupId)
                        .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedSendMsgToGroup(groupId)
                        .blockFirst(Utilities.IT_TIMEOUT));
        if (block != null) {
            throw new RuntimeException(
                    String.format("%s exist in %s group mute list", randomMemberUsername, groupId));
        }
        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGroupAssign(){
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(()->this.service.group().assignGroup(groupId,randomMemberUsername).block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

}
