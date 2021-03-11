package com.easemob.im.server;

import org.junit.jupiter.api.Test;

public class EMServiceTest {

    @Test
    public void testPrintBanner() {
        EMProperties properties = EMProperties.builder()
            .setBaseUri("https://a1.easemob.com")
            .setAppkey("easemob#demo")
            .setClientId("id")
            .setClientSecret("secret")
            .build();
        new EMService(properties);
    }
}