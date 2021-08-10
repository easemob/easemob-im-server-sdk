package com.easemob.im.server;

public interface Credentials {

    String getAppKey();

    default String getClientId() {
        return null;
    }

    default String getClientSecret() {
        return null;
    }

    default String getAppId() {
        return null;
    }

    default String getAppCert() {
        return null;
    }
}
