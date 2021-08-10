package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMPropertiesTest {

    private static final String DUMMY_APP_KEY = "dummyOrg#dummyApp";
    private static final String DUMMY_CLIENT_ID = "dummyClientId";
    private static final String DUMMY_CLIENT_SECRET = "dummyClientSecret";
    private static final String DUMMY_APP_ID = "dummyAppId";
    private static final String DUMMY_APP_CERT = "dummyAppCert";

    @Test
    public void buildDefaultProperties() {
        EMProperties properties = EMProperties.builder()
                .setAppkey(DUMMY_APP_KEY)
                .setClientId(DUMMY_CLIENT_ID)
                .setClientSecret(DUMMY_CLIENT_SECRET)
                .build();
        assertEquals(DUMMY_APP_KEY, properties.getAppkey());
        assertEquals(DUMMY_CLIENT_ID, properties.getClientId());
        assertEquals(DUMMY_CLIENT_SECRET, properties.getClientSecret());
    }

    @Test
    public void buildEasemobRealmProperties() {
        EMProperties properties = EMProperties.builder()
                .setRealm(EMProperties.Realm.EASEMOB_REALM)
                .setAppkey(DUMMY_APP_KEY)
                .setClientId(DUMMY_CLIENT_ID)
                .setClientSecret(DUMMY_CLIENT_SECRET)
                .build();
        assertEquals(DUMMY_APP_KEY, properties.getAppkey());
        assertEquals(DUMMY_CLIENT_ID, properties.getClientId());
        assertEquals(DUMMY_CLIENT_SECRET, properties.getClientSecret());
    }

    @Test
    public void buildAgoraRealmProperties() {
        EMProperties properties = EMProperties.builder()
                .setRealm(EMProperties.Realm.AGORA_REALM)
                .setAppkey(DUMMY_APP_KEY)
                .setAppId(DUMMY_APP_ID)
                .setAppCert(DUMMY_APP_CERT)
                .build();
        assertEquals(DUMMY_APP_KEY, properties.getAppkey());
        assertEquals(DUMMY_APP_ID, properties.getAppId());
        assertEquals(DUMMY_APP_CERT, properties.getAppCert());
    }

    @Test
    public void testInvalidAppkeyOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setAppkey(""));
    }

    @Test
    public void testInvalidAppkeyOfNull() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setAppkey(null));
    }

    @Test
    public void testInvalidClientIdOfEmpty() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientId(""));
    }

    @Test
    public void testInvalidClientIdOfNull() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientId(null));
    }

    @Test
    public void testInvalidClientSecretOfEmpty() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientSecret(""));
    }

    @Test
    public void testInvalidClientSecretOfNull() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientSecret(null));
    }

    @Test
    public void testNotSettingAppkey() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
                .setClientId("id")
                .setClientSecret("secret")
                .build());
    }

    @Test
    public void testNotSettingClientId() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
                .setAppkey("easemob#demo")
                .setClientSecret("secret")
                .build());
    }

    @Test
    public void testNotSettingClientSecret() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
                .setAppkey("easemob#demo")
                .setClientId("id")
                .build());
    }
}
