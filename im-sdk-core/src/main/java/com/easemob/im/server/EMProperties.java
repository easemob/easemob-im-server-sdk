package com.easemob.im.server;

import com.easemob.im.server.api.util.Sensitive;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMUnsupportedEncodingException;
import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;

/**
 * Server SDK配置类
 */
public class EMProperties {

    // Easemob OR Agora
    private final Realm realm;
    // clientId OR appId
    private final String clientId;
    // clientSecret OR appCertificate
    private final String clientSecret;
    // token expire elapse
    // TODO: Ken hard code expire elapse time for now
    private final int expire = 100;

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

    // preserve this for backwards compatibility
    public EMProperties(String baseUri, String appkey, EMProxy proxy, String clientId,
            String clientSecret, int httpConnectionPoolSize, String serverTimezone) {
        // easemob realm by default
        this.realm = Realm.EASEMOB_REALM;
        this.baseUri = baseUri;
        this.appkey = appkey;
        this.proxy = proxy;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
    }

    private EMProperties(Realm realm, String baseUri, String appkey, EMProxy proxy, String clientId,
            String clientSecret, int httpConnectionPoolSize, String serverTimezone) {
        this.realm = realm;
        this.baseUri = baseUri;
        this.appkey = appkey;
        this.proxy = proxy;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
    }

    public static Builder builder() {
        return new Builder(Realm.EASEMOB_REALM);
    }

    public static Builder builder(Realm realm) {
        return new Builder(realm);
    }

    public Realm getRealm() {
        return realm;
    }

    public String getAppId() {
        return this.clientId;
    }

    public String getAppCertificate() {
        return this.clientSecret;
    }

    public int getExpire() {
        return this.expire;
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

    @Override
    public String toString() {
        return "EMProperties{" +
                "baseUri='" + baseUri + '\'' +
                ", appkey='" + appkey + '\'' +
                ", proxy=" + proxy +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", httpConnectionPoolSize=" + httpConnectionPoolSize +
                ", serverTimezone='" + serverTimezone + '\'' +
                '}';
    }


    public static class Builder {
        private final Realm realm;

        private String baseUri;
        private String appkey;
        private EMProxy proxy;
        private String clientId;
        private String clientSecret;
        private Path downloadDir;
        private int httpConnectionPoolSize = 10;
        private String serverTimezone = "+8";


        public Builder(Realm realm) {
            this.realm = realm;
        }
        /**
         * 设置rest服务域名。
         * 该信息为可选，可以不进行设置，Server SDK会自动根据appkey请求到对应的rest服务的baseUri。
         *
         * @param baseUri baseUri
         * @return {@code Builder}
         */
        public Builder setBaseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        /**
         * 设置Appkey，可以到环信Console查询该值。
         *
         * @param appkey appkey
         * @return {@code Builder}
         */
        public Builder setAppkey(String appkey) {
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

            this.appkey = appkey;
            return this;
        }

        /**
         * 设置代理
         *
         * @param proxy proxy
         * @return {@code Builder}
         */
        public Builder setProxy(EMProxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setAppId(String appId) {
            if (Strings.isBlank(appId)) {
                throw new EMInvalidArgumentException("appId must not be null or blank");
            }
            this.clientId = appId;
            return this;
        }

        public Builder setAppCertificate(String appCertificate) {
            if (Strings.isBlank(appCertificate)) {
                throw new EMInvalidArgumentException("appCertificate must not be null or blank");
            }
            this.clientSecret = appCertificate;
            return this;
        }

        /**
         * 设置App认证id。
         * 该信息应该安全的保存在受信任的环境中。
         *
         * @param clientId 认证id
         * @return {@code Builder}
         */
        public Builder setClientId(String clientId) {
            if (Strings.isBlank(clientId)) {
                throw new EMInvalidArgumentException("clientId must not be null or blank");
            }

            this.clientId = clientId;
            return this;
        }

        /**
         * 设置App认证secret。
         * 该信息应该安全的保存在受信任的环境中。
         *
         * @param clientSecret 认证密码
         * @return {@code Builder}
         */
        public Builder setClientSecret(String clientSecret) {
            if (Strings.isBlank(clientSecret)) {
                throw new EMInvalidArgumentException("clientSecret must not be null or blank");
            }

            this.clientSecret = clientSecret;
            return this;
        }

        public Builder setHttpConnectionPoolSize(int httpConnectionPoolSize) {
            if (httpConnectionPoolSize < 0) {
                throw new EMInvalidArgumentException("httpConnectionPoolSize must not be negative");
            }

            this.httpConnectionPoolSize = httpConnectionPoolSize;
            return this;
        }

        public Builder setServerTimezone(String timezone) {
            this.serverTimezone = timezone;
            return this;
        }

        /**
         * 构造EMProperties
         *
         * @return {@code EMProperties}
         */
        public EMProperties build() {
            if (this.realm == null) {
                throw new EMInvalidStateException("realm not set");
            }
            if (this.appkey == null) {
                throw new EMInvalidStateException("appkey not set");
            }
            if (this.clientId == null) {
                String msg = realm == Realm.AGORA_REALM ? "appId not set" : "clientId not set";
                throw new EMInvalidStateException(msg);
            }
            if (this.clientSecret == null) {
                String msg = realm == Realm.AGORA_REALM ? "appCertificate not set" : "clientSecret not set";
                throw new EMInvalidStateException(msg);
            }

            return new EMProperties(this.realm, this.baseUri, this.appkey, this.proxy, this.clientId,
                    this.clientSecret, this.httpConnectionPoolSize, this.serverTimezone);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "realm='" + realm.toString() + '\'' +
                    "baseUri='" + baseUri + '\'' +
                    ", appkey='" + appkey + '\'' +
                    ", proxy=" + proxy +
                    ", clientId='" + Sensitive.mask(clientId) + '\'' +
                    ", clientSecret='" + Sensitive.mask(clientSecret) + '\'' +
                    ", downloadDir=" + downloadDir +
                    ", httpConnectionPoolSize=" + httpConnectionPoolSize +
                    ", serverTimezone='" + serverTimezone + '\'' +
                    '}';
        }
    }
}
