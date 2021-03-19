package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;

public class AbstractIT {

    protected EMService service;

    public AbstractIT() {
        String appkey = System.getenv("IM_APPKEY");
        String clientId = System.getenv("IM_CLIENT_ID");
        String clientSecret = System.getenv("IM_CLIENT_SECRET");

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
