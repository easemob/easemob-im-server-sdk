package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import io.agora.media.AccessToken2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgoraTokenProviderTest {

    private static final String appId = "970CA35de60c44645bbae8a215061b33";
    private static final String appCertificate = "5CFd2fd1755d40ecb72977518be15d3b";
    private static final String userId = "test_user";
    private static final int expire = 600;

    @Test
    public void fetchAppToken() {
        TokenProvider tokenProvider = new AgoraTokenProvider(appId, appCertificate, expire);
        Token appToken = tokenProvider.fetchAppToken().block();
        String appTokenValue = appToken.getValue();
        AccessToken2 accessToken = new AccessToken2();
        accessToken.parse(appTokenValue);

        assertEquals(appId, accessToken.appId);
        assertEquals(expire, accessToken.expire);
        assertEquals("", ((AccessToken2.ServiceChat)accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT)).getUserId());
        assertEquals(expire, (int)accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT).getPrivileges().get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP.intValue));
    }

    @Test
    public void fetchUserToken() {
        TokenProvider tokenProvider = new AgoraTokenProvider(appId, appCertificate, expire);
        Token userToken = tokenProvider.fetchUserToken(userId, null).block();
        String userTokenValue = userToken.getValue();
        AccessToken2 accessToken = new AccessToken2();
        accessToken.parse(userTokenValue);

        assertEquals(appId, accessToken.appId);
        assertEquals(expire, accessToken.expire);
        assertEquals(userId, ((AccessToken2.ServiceChat)accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT)).getUserId());
        assertEquals(expire, (int)accessToken.services.get(AccessToken2.SERVICE_TYPE_CHAT).getPrivileges().get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER.intValue));
    }
}
