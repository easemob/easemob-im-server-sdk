package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import reactor.netty.ReactorNetty;

public class AbstractIT {

    protected EMService service;

    public AbstractIT() {
        String realm = System.getenv("IM_REALM");
//        String appkey = System.getenv("IM_APPKEY");
        String appkey = "62242102#90";
        String baseUri = System.getenv("IM_BASE_URI");
        System.setProperty(ReactorNetty.IO_WORKER_COUNT,"256");
        System.setProperty(ReactorNetty.SSL_HANDSHAKE_TIMEOUT, "60000");

        EMProperties properties = null;

        if (realm != null && realm.equals(EMProperties.Realm.AGORA_REALM.toString())) {
            String appId = System.getenv("IM_APP_ID");
            String appCert = System.getenv("IM_APP_CERT");

            properties = EMProperties.builder()
                    .setBaseUri(baseUri)
                    .setRealm(EMProperties.Realm.AGORA_REALM)
                    .setAppkey(appkey)
                    .setAppId(appId)
                    .setAppCert(appCert)
                    .build();

        } else {
//            String clientId = System.getenv("IM_CLIENT_ID");
//            String clientSecret = System.getenv("IM_CLIENT_SECRET");
            String clientId = "YXA6a1jQXZnASMeiRB_z6Vo9wA";
            String clientSecret = "YXA6LDJ_YHmppwgccxHNEZmyMnjWy1E";

            properties = EMProperties.builder()
                    .setRealm(EMProperties.Realm.EASEMOB_REALM)
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
