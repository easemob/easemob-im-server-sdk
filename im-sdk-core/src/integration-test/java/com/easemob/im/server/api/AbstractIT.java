package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;

public class AbstractIT {

    protected EMService service;

    public AbstractIT() {
        String realm = System.getenv("IM_REALM");
        String appkey = System.getenv("IM_APPKEY");
        String baseUri = System.getenv("IM_BASE_URI");

        EMProperties properties = null;

        if (realm != null && realm.equals(EMProperties.Realm.AGORA_REALM.toString())) {
            String appId = System.getenv("IM_APP_ID");
            String appCert = System.getenv("IM_APP_CERT");

            properties = EMProperties.agoraRealmBuilder()
                    .setBaseUri(baseUri)
                    .setAppkey(appkey)
                    .setAppId(appId)
                    .setAppCert(appCert)
                    .build();

        } else {
            String clientId = System.getenv("IM_CLIENT_ID");
            String clientSecret = System.getenv("IM_CLIENT_SECRET");

            properties = EMProperties.builder()
                    .setBaseUri(baseUri)
                    .setAppkey(appkey)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setHttpConnectionPoolSize(10)
                    .setServerTimezone("+8")
                    .build();
        }

        this.service = new EMService(properties);
    }
}
