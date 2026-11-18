package com.easemob.im;

import com.google.common.cache.Cache;
import io.agora.media.AccessToken2;
import okhttp3.Request;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiClientAgoraTokenTest {
    private static final String APP_ID = "0123456789abcdef0123456789abcdef";
    private static final String APP_CERT = "fedcba9876543210fedcba9876543210";

    @Test
    void appTokenHasApplicationPrivilegeAndOneDayLifetime() throws Exception {
        String token = agoraClient().getAppToken();
        AccessToken2 parsed = new AccessToken2();

        assertTrue(parsed.parse(token));
        assertEquals(APP_ID, parsed.appId);
        assertEquals(86400, parsed.expire);
        AccessToken2.Service chat = parsed.services.get(AccessToken2.SERVICE_TYPE_CHAT);
        assertNotNull(chat);
        assertEquals(Integer.valueOf(86400),
                chat.getPrivileges().get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP.intValue));

        // Rebuild with the same timestamp and salt to verify the certificate-based signature.
        parsed.appCert = APP_CERT;
        assertEquals(token, parsed.build());
    }

    @Test
    void repeatedReadsAndRestRequestsReuseCachedToken() throws ApiException {
        ApiClient client = agoraClient();
        String token = client.getAppToken();

        assertSame(token, client.getAppToken());
        assertEquals("Bearer " + token, usersRequest(client).header("Authorization"));
        assertSame(token, client.getAppToken());
    }

    @Test
    void cacheMissRegeneratesTokenForRestRequests() throws Exception {
        ApiClient client = agoraClient();
        String oldToken = client.getAppToken();
        Field cacheField = ApiClient.class.getDeclaredField("appTokenCache");
        cacheField.setAccessible(true);
        Cache<?, ?> cache = (Cache<?, ?>) cacheField.get(client);

        // Force a cache miss without waiting for the production 12-hour expiration.
        cache.invalidateAll();
        String refreshedToken = client.getAppToken();

        assertNotSame(oldToken, refreshedToken);
        assertSame(refreshedToken, client.getAppToken());
        assertEquals("Bearer " + refreshedToken, usersRequest(client).header("Authorization"));
    }

    private static ApiClient agoraClient() throws ApiException {
        return ApiClient.builder()
                .setRealm(ApiClient.Realm.AGORA_REALM)
                .setBasePath("https://api.example.com")
                .setAppId(APP_ID)
                .setAppCert(APP_CERT)
                .build();
    }

    private static Request usersRequest(ApiClient client) throws ApiException {
        return client.buildRequest(null, "/users", "GET", new ArrayList<>(), new ArrayList<>(),
                null, new HashMap<>(), new HashMap<>(), new HashMap<>(), new String[0], null);
    }
}
