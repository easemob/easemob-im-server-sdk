package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgoraTokenProviderTest {

    private static final int EXPIRE_SECONDS = 600;

    private static final String DUMMY_APP_ID = "970CA35de60c44645bbae8a215061b33";
    private static final String DUMMY_APP_CERT = "5CFd2fd1755d40ecb72977518be15d3b";
    private static final String DUMMY_USER_ID = "test_user";

    @Test
    public void fetchAppToken() {
        TokenProvider tokenProvider = new AgoraTokenProvider(DUMMY_APP_ID, DUMMY_APP_CERT);
        Token appToken = tokenProvider.fetchAppToken().block();
        String appTokenValue = appToken.getValue();
        AccessToken2 accessToken = new AccessToken2();
        accessToken.parse(appTokenValue);

        assertEquals(DUMMY_APP_ID, accessToken.appId);
        assertEquals(EXPIRE_SECONDS, accessToken.expire);
        assertEquals("", ((AccessToken2.ServiceChat)accessToken.services
                .get(AccessToken2.SERVICE_TYPE_CHAT)).getUserId());
        assertEquals(
                EXPIRE_SECONDS, (int)accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT)
                        .getPrivileges().get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP.intValue)
        );
    }

    @Test
    public void builderUserToken() throws Exception {
        TokenProvider tokenProvider = new AgoraTokenProvider(DUMMY_APP_ID, DUMMY_APP_CERT);
        Token userToken = tokenProvider.buildUserToken(DUMMY_USER_ID,
                EXPIRE_SECONDS, token -> {}).block();
        String userTokenValue = userToken.getValue();
        AccessToken2 accessToken = new AccessToken2();
        accessToken.parse(userTokenValue);

        assertEquals(DUMMY_APP_ID, accessToken.appId);
        assertEquals(EXPIRE_SECONDS, accessToken.expire);
        assertEquals(DUMMY_USER_ID, ((AccessToken2.ServiceChat)accessToken.services
                .get(AccessToken2.SERVICE_TYPE_CHAT)).getUserId());
        assertEquals(EXPIRE_SECONDS, (int)accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT)
                .getPrivileges().get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER.intValue)
        );
    }
}
