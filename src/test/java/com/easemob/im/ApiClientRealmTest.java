package com.easemob.im;

import okhttp3.Request;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiClientRealmTest {
    private static final String BASE_PATH = "https://api.example.com";
    private static final String APP_ID = "0123456789abcdef0123456789abcdef";
    private static final String APP_CERT = "fedcba9876543210fedcba9876543210";

    @Test
    void agoraRequestsUseAppIdPathWithoutAppKey() throws ApiException {
        ApiClient apiClient = ApiClient.builder()
                .setRealm(ApiClient.Realm.AGORA_REALM)
                .setBasePath(BASE_PATH)
                .setAppId(APP_ID)
                .setAppCert(APP_CERT)
                .build();

        Request request = apiClient.buildRequest(
                null,
                "/users",
                "GET",
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new String[0],
                null
        );

        assertEquals(ApiClient.Realm.AGORA_REALM, apiClient.getRealm());
        assertEquals(BASE_PATH + "/app-id/" + APP_ID + "/users", request.url().toString());
    }

    @Test
    void easemobRequestsKeepOrgAndAppPath() throws ApiException {
        ApiClient apiClient = new StubApiClient(
                BASE_PATH,
                ApiClient.Realm.EASEMOB_REALM,
                "org#app",
                "client-id",
                "client-secret",
                null,
                null
        );

        Request request = apiClient.buildRequest(
                null,
                "/users",
                "GET",
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new String[0],
                null
        );

        assertEquals(ApiClient.Realm.EASEMOB_REALM, apiClient.getRealm());
        assertEquals(BASE_PATH + "/org/app/users", request.url().toString());
    }

    @Test
    void agoraRequiresAppIdAndAppCert() {
        assertThrows(IllegalArgumentException.class, () -> ApiClient.builder()
                .setRealm(ApiClient.Realm.AGORA_REALM)
                .setBasePath(BASE_PATH)
                .setAppCert(APP_CERT)
                .build());

        assertThrows(IllegalArgumentException.class, () -> ApiClient.builder()
                .setRealm(ApiClient.Realm.AGORA_REALM)
                .setBasePath(BASE_PATH)
                .setAppId(APP_ID)
                .build());
    }

    @Test
    void easemobRequiresAppKeyClientIdAndClientSecret() {
        assertThrows(IllegalArgumentException.class, () -> ApiClient.builder()
                .setBasePath(BASE_PATH)
                .setClientId("client-id")
                .setClientSecret("client-secret")
                .build());

        assertThrows(IllegalArgumentException.class, () -> ApiClient.builder()
                .setBasePath(BASE_PATH)
                .setAppKey("org#app")
                .setClientSecret("client-secret")
                .build());

        assertThrows(IllegalArgumentException.class, () -> ApiClient.builder()
                .setBasePath(BASE_PATH)
                .setAppKey("org#app")
                .setClientId("client-id")
                .build());
    }

    @Test
    void easemobRejectsAgoraCredentials() {
        assertThrows(IllegalArgumentException.class, () -> ApiClient.builder()
                .setBasePath(BASE_PATH)
                .setAppKey("org#app")
                .setClientId("client-id")
                .setClientSecret("client-secret")
                .setAppId(APP_ID)
                .build());
    }

    private static class StubApiClient extends ApiClient {
        StubApiClient(String basePath, Realm realm, String appKey, String clientId, String clientSecret,
                String appId, String appCert) throws ApiException {
            super(basePath, realm, appKey, clientId, clientSecret, appId, appCert,
                    1, 1000, 1000, null, 1, 1);
        }

        @Override
        public String getAppToken() {
            return "test-token";
        }
    }
}
