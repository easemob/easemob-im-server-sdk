package com.easemob.im.server;

import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.apache.logging.log4j.util.Strings;

public class AgoraAppCredentials implements Credentials {
    private final String appKey;
    private final String appId;
    private final String appCert;

    public AgoraAppCredentials(String appKey, String appId, String appCert) {
        if (Strings.isBlank(appKey) || Strings.isBlank(appId) || Strings.isBlank(appCert)) {
            throw new EMInvalidArgumentException("appKey/appId/appCert cannot be blank");
        }
        this.appKey = appKey;
        this.appId = appId;
        this.appCert = appCert;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getAppCert() {
        return appCert;
    }

    @Override public String toString() {
        return "AgoraAppCredentials{" +
                "appKey='" + appKey + '\'' +
                ", appId='" + Utilities.mask(appId) + '\'' +
                ", appCert='" + Utilities.mask(appCert) + '\'' +
                '}';
    }
}
