package com.easemob.im.server.integration;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;

public class AbstractIT {

    protected EMService service;

    public AbstractIT() {
        System.out.println("AbstractIT Initializing");
        String appkey = "";
        String clientId = "";
        String clientSecret = "";

        EMProperties properties = EMProperties.builder()
                .setAppkey(appkey)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setHttpConnectionPoolSize(10)
                .setServerTimezone("+8")
                .build();

        this.service = new EMService(properties);
    }
}
