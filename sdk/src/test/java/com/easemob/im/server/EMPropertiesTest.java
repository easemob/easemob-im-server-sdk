package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMPropertiesTest {
    @Test
    public void testBuildEMPropertiesSuccessfully() {
        EMProperties properties = EMProperties.builder()
            .baseUri("https://a1.easemob.com")
            .appkey("easemob#demo")
            .clientId("id")
            .clientSecret("secret")
            .build();
        assertEquals("https://a1.easemob.com/easemob/demo", properties.getBaseUri());
        assertEquals("easemob#demo", properties.getAppkey());
        assertEquals( "id", properties.getClientId());
        assertEquals("secret", properties.getClientSecret());
    }

    @Test
    public void testTrimmingBaseUri() {
        EMProperties properties = EMProperties.builder()
            .baseUri("https://a1.easemob.com/")
            .appkey("easemob#demo")
            .clientId("id")
            .clientSecret("secret")
            .build();
        assertEquals("https://a1.easemob.com/easemob/demo", properties.getBaseUri());
        assertEquals("easemob#demo", properties.getAppkey());
        assertEquals( "id", properties.getClientId());
        assertEquals("secret", properties.getClientSecret());
    }

    @Test
    public void testInvalidBaseUriOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().baseUri(""));
    }

    @Test
    public void testInvalidBaseUriOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().baseUri(null));
    }


    @Test
    public void testInvalidAppkeyOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().appkey(""));
    }

    @Test
    public void testInvalidAppkeyOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().appkey(null));
    }


    @Test
    public void testInvalidClientIdOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().clientId(""));
    }

    @Test
    public void testInvalidClientIdOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().clientId(null));
    }


    @Test
    public void testInvalidClientSecretOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().clientSecret(""));
    }

    @Test
    public void testInvalidClientSecretOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().clientSecret(null));
    }

    @Test
    public void testNotSettingBaseUri() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .appkey("easemob#demo")
            .clientId("id")
            .clientSecret("secret")
            .build());
    }

    @Test
    public void testNotSettingAppkey() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .baseUri("https://a1.easemob.com")
            .clientId("id")
            .clientSecret("secret")
            .build());
    }

    @Test
    public void testNotSettingClientId() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .baseUri("https://a1.easemob.com")
            .appkey("easemob#demo")
            .clientSecret("secret")
            .build());
    }

    @Test
    public void testNotSettingClientSecret() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .baseUri("https://a1.easemob.com")
            .appkey("easemob#demo")
            .clientId("id")
            .build());
    }
}