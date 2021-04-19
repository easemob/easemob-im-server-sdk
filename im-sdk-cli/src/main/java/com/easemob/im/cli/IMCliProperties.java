package com.easemob.im.cli;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "im")
public class IMCliProperties {
    private String domain;
    private String appkey;
    private String clientId;
    private String clientSecret;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


}
