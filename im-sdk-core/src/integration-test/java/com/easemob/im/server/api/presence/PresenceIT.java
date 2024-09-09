package com.easemob.im.server.api.presence;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.presence.subscribe.PresenceUserStatusSubscribeResult;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PresenceIT extends AbstractIT {

    PresenceIT() {super();}

    @Test
    void testGetUserStatus() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        String resource = "ios_100000";
        String status = "1";
        String ext = "在线";

        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.presence().setUserStatus(randomUsername, resource, status, ext)
                .block(Utilities.IT_TIMEOUT));

        List<PresenceUserStatusResource> userStatusList = assertDoesNotThrow(() -> this.service.presence().getUserStatus("u1", Arrays.asList(randomUsername))
                .block(Utilities.IT_TIMEOUT));
        assertNotNull(userStatusList);

        userStatusList.forEach(userStatus -> {
            Map<String, String> us = (Map<String, String>) userStatus.getStatus();
            assertEquals(status, us.get(resource));
            assertEquals(ext, userStatus.getExt());
        });

        List<PresenceUserStatusResource> userStatusList1 = assertDoesNotThrow(() -> this.service.presence().getUserStatus("u1", Arrays.asList("u10000"))
                .block(Utilities.IT_TIMEOUT));
        assertNotNull(userStatusList1);

        userStatusList1.forEach(userStatus -> {
            List us = (List) userStatus.getStatus();
            assertEquals(0, us.size());
        });

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testSubscribeUserStatus() throws InterruptedException {
        String randomUsername1 = Utilities.randomUserName();
        String randomUsername2 = Utilities.randomUserName();
        String randomUsername3 = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        String resource = "ios_100000";
        String status = "1";
        String ext = "在线";

        assertDoesNotThrow(() -> this.service.user().create(randomUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername2, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername3, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.presence().setUserStatus(randomUsername1, resource, status, ext)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.presence().setUserStatus(randomUsername2, resource, status, ext)
                .block(Utilities.IT_TIMEOUT));

        List<PresenceUserStatusSubscribeResource> subscribeUserStatusList = assertDoesNotThrow(() -> this.service.presence().subscribeUserStatus(randomUsername3, Arrays.asList(randomUsername1, randomUsername2), 86400L)
                .block(Utilities.IT_TIMEOUT));
        assertNotNull(subscribeUserStatusList);
        assertEquals(2, subscribeUserStatusList.size());

        Thread.sleep(10000);

        PresenceUserStatusSubscribeResult
                subscribeResult = assertDoesNotThrow(() -> this.service.presence().getSubscribeList(randomUsername3, 1, 10)
                .block(Utilities.IT_TIMEOUT));
        assertNotNull(subscribeResult);
        assertNotNull(subscribeResult.getSubscribeResourceList());
        assertEquals("2", subscribeResult.getTotalNumber());

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername1)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername2)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername3)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUnsubscribeUserStatus() throws InterruptedException {
        String randomUsername1 = Utilities.randomUserName();
        String randomUsername2 = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        String resource = "ios_100000";
        String status = "1";
        String ext = "在线";

        assertDoesNotThrow(() -> this.service.user().create(randomUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername2, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.presence().setUserStatus(randomUsername1, resource, status, ext)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.presence().subscribeUserStatus(randomUsername2, Arrays.asList(randomUsername1), 86400L)
                .block(Utilities.IT_TIMEOUT));

        Thread.sleep(10000);

        PresenceUserStatusSubscribeResult
                subscribeResult = assertDoesNotThrow(() -> this.service.presence().getSubscribeList(randomUsername2, 1, 10)
                .block(Utilities.IT_TIMEOUT));
        assertNotNull(subscribeResult);
        assertNotNull(subscribeResult.getSubscribeResourceList());
        assertEquals("1", subscribeResult.getTotalNumber());

        subscribeResult.getSubscribeResourceList().forEach(subscribeResource -> {
            assertEquals(randomUsername1, subscribeResource.getUid());
        });

        assertDoesNotThrow(() -> this.service.presence().unsubscribeUserStatus(randomUsername2, Arrays.asList(randomUsername1))
                .block(Utilities.IT_TIMEOUT));

        Thread.sleep(10000);

        PresenceUserStatusSubscribeResult
                subscribeResult1 = assertDoesNotThrow(() -> this.service.presence().getSubscribeList(randomUsername2, 1, 10)
                .block(Utilities.IT_TIMEOUT));
        assertNotNull(subscribeResult1);
        assertEquals("0", subscribeResult1.getTotalNumber());

        subscribeResult.getSubscribeResourceList().forEach(subscribeResource -> {
            assertEquals(randomUsername1, subscribeResource.getUid());
        });

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername1)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername2)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGetOnlineCount() throws InterruptedException {
//        String randomOwnerUsername = Utilities.randomUserName();
//        String randomPassword = Utilities.randomPassword();
//
//        String randomMemberUsername = Utilities.randomUserName();
//        List<String> members = new ArrayList<>();
//        members.add(randomMemberUsername);
//        String resource = "ios_100000";
//        String status = "1";
//        String ext = "在线";
//
//        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
//                .block(Utilities.IT_TIMEOUT));
//        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
//                .block(Utilities.IT_TIMEOUT));
//
//        String groupId = assertDoesNotThrow(() -> this.service.group()
//                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
//                        true).block(Utilities.IT_TIMEOUT));
//
//        assertDoesNotThrow(() -> this.service.presence().setUserStatus(randomOwnerUsername, resource, status, ext)
//                .block(Utilities.IT_TIMEOUT));
//
//        assertDoesNotThrow(() -> this.service.presence().setUserStatus(randomMemberUsername, resource, status, ext)
//                .block(Utilities.IT_TIMEOUT));
//
//        Thread.sleep(3000);
//
//        Integer onlineCount = assertDoesNotThrow(() -> this.service.presence().getUserOnlineCount(groupId, 1)
//                .block(Utilities.IT_TIMEOUT));
//        assertEquals(2, onlineCount);
//
//        assertDoesNotThrow(
//                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
//        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername)
//                .block(Utilities.IT_TIMEOUT));
//        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
//                .block(Utilities.IT_TIMEOUT));
    }

}
