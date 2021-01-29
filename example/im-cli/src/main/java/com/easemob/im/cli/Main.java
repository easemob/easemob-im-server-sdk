package com.easemob.im.cli;

import com.easemob.im.server.EMLog;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;

public class Main {
    public static void main(String[] args) {
        EMLog.setLogLevel(EMLog.Level.TRACE);
        EMProperties properties = EMProperties.builder()
            .baseUri("https://a1.easemob.com/")
            .appkey("easemob#demo")
            .clientId("clientId")
            .clientSecret("clientSecret")
            .build();
        EMService service = new EMService(properties);
        service.tokenV1().allocate().forUser("tom", "tom").block();
    }
}
