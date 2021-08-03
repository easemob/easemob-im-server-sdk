package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.exception.EMForbiddenException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccessToken2UtilsTest {

    private static final String DUMMY_APP_ID = "970CA35de60c44645bbae8a215061b33";
    private static final String DUMMY_APP_CERT = "5CFd2fd1755d40ecb72977518be15d3b";
    private static final String DUMMY_USER_NAME = "test_user";
    private static final String DUMMY_USER_ID = "da9287a0-ecf9-11eb-9af3-296ff79acb67";
    private static final int DUMMY_EXPIRE_SECONDS = 600;

    private static final AccessToken2.PrivilegeRtc DUMMY_RTC_PRIVILEGE =
            AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL;
    private static final String DUMMY_CHANNEL_NAME = "dummyChannelName";
    private static final String DUMMY_UID = "dummyUID";

    @Test
    public void buildAppToken() {
        String token = AccessToken2Utils.buildAppToken(DUMMY_APP_ID, DUMMY_APP_CERT,
                DUMMY_EXPIRE_SECONDS);
        AccessToken2 accessToken = new AccessToken2();
        accessToken.parse(token);

        assertEquals(DUMMY_APP_ID, accessToken.appId);
        assertEquals(DUMMY_EXPIRE_SECONDS, accessToken.expire);
        assertEquals("", ((AccessToken2.ServiceChat) accessToken.services
                .get(AccessToken2.SERVICE_TYPE_CHAT)).getUserId());
        assertEquals(
                DUMMY_EXPIRE_SECONDS, (int) accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT)
                        .getPrivileges().get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP.intValue)
        );
    }

    @Test
    public void buildUserChatToken() {
        String token = AccessToken2Utils.buildUserChatToken(DUMMY_APP_ID, DUMMY_APP_CERT,
                DUMMY_USER_NAME, DUMMY_EXPIRE_SECONDS);
        AccessToken2 accessToken = new AccessToken2();
        accessToken.parse(token);

        assertEquals(DUMMY_APP_ID, accessToken.appId);
        assertEquals(DUMMY_EXPIRE_SECONDS, accessToken.expire);
        assertEquals(DUMMY_USER_NAME, ((AccessToken2.ServiceChat) accessToken.services
                .get(AccessToken2.SERVICE_TYPE_CHAT)).getUserId());
        assertEquals(
                DUMMY_EXPIRE_SECONDS, (int) accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT)
                        .getPrivileges()
                        .get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER.intValue));
    }

    @Test
    public void buildUserChatRtcToken() {
        String customizedTokenValue = AccessToken2Utils.buildUserCustomizedToken(
                DUMMY_APP_ID, DUMMY_APP_CERT, DUMMY_USER_ID, DUMMY_EXPIRE_SECONDS,
                        AccessToken2Utils.rtcPrivilegeAdder(DUMMY_CHANNEL_NAME, DUMMY_UID,
                                DUMMY_RTC_PRIVILEGE, DUMMY_EXPIRE_SECONDS)
                );

        AccessToken2 chatRtcToken = new AccessToken2();
        chatRtcToken.parse(customizedTokenValue);

        Map<Short, AccessToken2.Service> services = chatRtcToken.services;
        assertEquals(2, services.size());

        AccessToken2.Service service1 = services.get(AccessToken2.SERVICE_TYPE_CHAT);
        AccessToken2.ServiceChat serviceChat = (AccessToken2.ServiceChat) service1;
        String userIdInToken = serviceChat.getUserId();
        assertEquals(DUMMY_USER_ID, userIdInToken);
        Map<Short, Integer> privilegesChat = service1.getPrivileges();
        assertEquals(1, privilegesChat.size());
        int expireInTokenChat = privilegesChat
                .get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER.intValue);
        assertEquals(DUMMY_EXPIRE_SECONDS, expireInTokenChat);

        AccessToken2.Service service2 = services.get(AccessToken2.SERVICE_TYPE_RTC);
        AccessToken2.ServiceRtc serviceRtc = (AccessToken2.ServiceRtc) service2;
        String channelName = serviceRtc.getChannelName();
        String uid = serviceRtc.getUid();
        assertEquals(DUMMY_CHANNEL_NAME, channelName);
        assertEquals(DUMMY_UID, uid);
        Map<Short, Integer> privilegesRtc = service2.getPrivileges();
        assertEquals(1, privilegesRtc.size());
        int expireInTokenRtc = privilegesRtc
                .get(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL.intValue);
        assertEquals(DUMMY_EXPIRE_SECONDS, expireInTokenRtc);
    }

    @Test
    public void buildInvalidUserToken() {
        // adding chat app privilege is not allowed
        assertThrows(EMForbiddenException.class, () -> {
            AccessToken2Utils.buildUserCustomizedToken(
                    DUMMY_APP_ID, DUMMY_APP_CERT, DUMMY_USER_ID, DUMMY_EXPIRE_SECONDS,
                    token -> {
                        AccessToken2.ServiceChat serviceChat =
                                (AccessToken2.ServiceChat) token.services
                                        .get(AccessToken2.SERVICE_TYPE_CHAT);
                        serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP,
                                DUMMY_EXPIRE_SECONDS);
                    });
        });
    }
}
