package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMUnsupportedEncodingException;
import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Server SDK配置类
 */
public class EMProperties {
    
    private static void validateAppkey(String appkey) {
        if (Strings.isBlank(appkey)) {
            throw new EMInvalidArgumentException("appkey must not be null or blank");
        }
        String[] tokens = appkey.split("#");
        if (tokens.length != 2) {
            throw new EMInvalidArgumentException("appkey must contains #");
        }
        if (tokens[0].isEmpty()) {
            throw new EMInvalidArgumentException("appkey must contains {org}");
        }
        if (tokens[1].isEmpty()) {
            throw new EMInvalidArgumentException("appkey must contains {app}");
        }
    }

    // Easemob only fields
    private String clientId;
    private String clientSecret;

    private String appId;
    private String appCert;

    private final Realm realm;
    private final String baseUri;
    private final String appkey;
    private final EMProxy proxy;
    private final int httpConnectionPoolSize;
    private final String serverTimezone;

    public enum Realm {
        AGORA_REALM(1),
        EASEMOB_REALM(2),
        ;
        public short intValue;
        Realm(int value) {
            intValue = (short) value;
        }
    }

    // keep this for backwards compatibility
    public static EasemobRealmBuilder builder() {
        return new EasemobRealmBuilder();
    }

    public static EasemobRealmBuilder easemobRealmBuilder() {
        return new EasemobRealmBuilder();
    }

    public static AgoraRealmBuilder agoraRealmBuilder() {
        return new AgoraRealmBuilder();
    }

    // preserve this for backwards compatibility
    public EMProperties(String baseUri, String appkey, EMProxy proxy, String clientId,
            String clientSecret, int httpConnectionPoolSize, String serverTimezone) {
        // easemob realm by default
        this(Realm.EASEMOB_REALM, baseUri, appkey, proxy, httpConnectionPoolSize, serverTimezone);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private EMProperties(Realm realm, String baseUri, String appkey, EMProxy proxy,
            int httpConnectionPoolSize, String serverTimezone) {
        this.realm = realm;
        this.baseUri = baseUri;
        this.appkey = appkey;
        this.proxy = proxy;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
    }

    private void setClientId(String clientId) {
        this.clientId = clientId;
    }

    private void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private void setAppId(String appId) {
        this.appId = appId;
    }

    private void setAppCert(String appCert) {
        this.appCert = appCert;
    }

    public Realm getRealm() {
        return realm;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppCert() {
        return this.appCert;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getAppkey() {
        return this.appkey;
    }

    public EMProxy getProxy() {
        return this.proxy;
    }

    public String getAppkeyUrlEncoded() {
        try {
            return URLEncoder.encode(this.appkey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EMUnsupportedEncodingException(e.getMessage());
        }
    }

    public String getAppkeySlashDelimited() {
        return this.appkey.replace('#', '/');
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public int getHttpConnectionPoolSize() {
        return this.httpConnectionPoolSize;
    }

    public String getServerTimezone() {
        return this.serverTimezone;
    }

    public static class AgoraRealmBuilder {
        private String appId;
        private String appCert;

        private String baseUri;
        private String appkey;
        private EMProxy proxy;
        private int httpConnectionPoolSize = 10;
        private String serverTimezone = "+8";

        public AgoraRealmBuilder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public AgoraRealmBuilder setAppCert(String appCert) {
            this.appCert = appCert;
            return this;
        }

        public AgoraRealmBuilder setBaseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        public AgoraRealmBuilder setAppkey(String appkey) {
            validateAppkey(appkey);
            this.appkey = appkey;
            return this;
        }

        public AgoraRealmBuilder setProxy(EMProxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public AgoraRealmBuilder setHttpConnectionPoolSize(int httpConnectionPoolSize) {
            if (httpConnectionPoolSize < 0) {
                throw new EMInvalidArgumentException("httpConnectionPoolSize must not be negative");
            }

            this.httpConnectionPoolSize = httpConnectionPoolSize;
            return this;
        }

        public AgoraRealmBuilder setServerTimezone(String timezone) {
            this.serverTimezone = timezone;
            return this;
        }

        public EMProperties build() {
            if (this.appkey == null) {
                throw new EMInvalidStateException("appkey not set");
            }
            if (this.appId == null) {
                throw new EMInvalidStateException("appId not set");
            }
            if (this.appCert == null) {
                throw new EMInvalidStateException("appCert not set");
            }
            EMProperties properties = new EMProperties(Realm.AGORA_REALM, baseUri, appkey, proxy,
                    httpConnectionPoolSize, serverTimezone);
            properties.setAppId(appId);
            properties.setAppCert(appCert);
            return properties;
        }
    }

    public static class EasemobRealmBuilder {

        private String clientId;
        private String clientSecret;

        private String baseUri;
        private String appkey;
        private EMProxy proxy;
        private int httpConnectionPoolSize = 10;
        private String serverTimezone = "+8";

        public EasemobRealmBuilder setBaseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        public EasemobRealmBuilder setAppkey(String appkey) {
            validateAppkey(appkey);
            this.appkey = appkey;
            return this;
        }

        public EasemobRealmBuilder setProxy(EMProxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public EasemobRealmBuilder setHttpConnectionPoolSize(int httpConnectionPoolSize) {
            if (httpConnectionPoolSize < 0) {
                throw new EMInvalidArgumentException("httpConnectionPoolSize must not be negative");
            }

            this.httpConnectionPoolSize = httpConnectionPoolSize;
            return this;
        }

        public EasemobRealmBuilder setServerTimezone(String timezone) {
            this.serverTimezone = timezone;
            return this;
        }

        public EasemobRealmBuilder setClientId(String clientId) {
            if (Strings.isBlank(clientId)) {
                throw new EMInvalidArgumentException("clientId must not be null or blank");
            }
            this.clientId = clientId;
            return this;
        }

        public EasemobRealmBuilder setClientSecret(String clientSecret) {
            if (Strings.isBlank(clientSecret)) {
                throw new EMInvalidArgumentException("clientSecret must not be null or blank");
            }
            this.clientSecret = clientSecret;
            return this;
        }

        public EMProperties build() {
            if (this.appkey == null) {
                throw new EMInvalidStateException("appkey not set");
            }
            if (this.clientId == null) {
                throw new EMInvalidStateException("clientId not set");
            }
            if (this.clientSecret == null) {
                throw new EMInvalidStateException("clientSecret not set");
            }
            EMProperties properties = new EMProperties(Realm.EASEMOB_REALM, baseUri, appkey, proxy,
                            httpConnectionPoolSize, serverTimezone);
            properties.setClientId(clientId);
            properties.setClientSecret(clientSecret);
            return properties;
        }
    }

}
