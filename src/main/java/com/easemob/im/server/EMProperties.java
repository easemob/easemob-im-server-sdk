package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;

/**
 * 配置app的参数，包括appKey，clientId，clientSecret，baseUri
 *
 * appKey，clientId，clientSecret的获取，请登录环信console后台-->应用列表-->选择应用，点击查看即可
 *
 * 环信console后台链接：https://console.easemob.com/user/login
 * 如果没有账号请"创建新账号"，登录后"添加应用"，即可获取到 appKey，clientId，clientSecret
 *
 * baseUri为环信rest服务的域名，默认为 https://a1.easemob.com
 *
 * 如果是vip集群的客户，请与对应的环信商务联系索要域名
 *
 */

public class EMProperties {

    private final String baseUri;
    private final String appkey;
    private final String clientId;
    private final String clientSecret;
    private final int httpConnectionPoolSize;

    public static Builder builder() {
        return new Builder();
    }

    public String getBaseUri() {
        return this.baseUri;
    }

    public String getAppkey() {
        return this.appkey;
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

    private EMProperties(String baseUri, String appkey, String clientId, String clientSecret, int httpConnectionPoolSize) {
        String[] tokens = appkey.split("#");
        this.baseUri = String.format("%s/%s/%s", baseUri, tokens[0], tokens[1]);
        this.appkey = appkey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
    }

    public static class Builder {
        private static final int DEFAULT_HTTP_CONNECTION_POOL_SIZE = 10;
        private String appkey;
        private String clientId;
        private String clientSecret;
        private String baseUri;
        private int httpConnectionPoolSize = DEFAULT_HTTP_CONNECTION_POOL_SIZE;

        /**
         * 设置Appkey，可以到环信Console查询该值。
         *
         * @param appkey
         * @return {@code Builder}
         */
        public Builder withAppkey(String appkey) {
            if (appkey == null || appkey.isEmpty()) {
                throw new EMInvalidArgumentException("appkey must not be null or empty");
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
         * 设置App认证id，可以到环信Console查询该值。
         * 该信息应该安全的保存在受信任的环境中。
         *
         * @param clientId     认证id
         * @return {@code Builder}
         */
        public Builder withClientId(String clientId) {
            if (clientId == null || clientId.isEmpty()) {
                throw new EMInvalidArgumentException("clientId must not be null or empty");
            }

            this.clientId = clientId;
            return this;
        }

        /**
         * 设置App认证secret，可以到环信Console查询该值。
         * 该信息应该安全的保存在受信任的环境中。
         *
         * @param clientSecret 认证密码
         * @return {@code Builder}
         */
        public Builder withClientSecret(String clientSecret) {
            if (clientSecret == null || clientSecret.isEmpty()) {
                throw new EMInvalidArgumentException("clientSecret must not be null or empty");
            }

            this.clientSecret = clientSecret;
            return this;
        }

        /**
         * 设置环信API baseUri，可以到环信Console查询该值。
         *
         * @param baseUri baseUri
         * @return the {@code Builder}
         */
        public Builder baseUri(String baseUri) {
            if (baseUri == null || baseUri.isEmpty()) {
                throw new EMInvalidArgumentException("baseUri must not be null or empty");
            }
            // trim trailing slash
            if (baseUri.charAt(baseUri.length()-1) == '/') {
                this.baseUri = baseUri.substring(0, baseUri.length()-1);
            } else {
                this.baseUri = baseUri;
            }

            return this;
        }

        public Builder httpConnectionPoolSize(int httpConnectionPoolSize) {
            if (httpConnectionPoolSize < 0) {
                throw new EMInvalidArgumentException("httpConnectionPoolSize must not be negative");
            }

            this.httpConnectionPoolSize = httpConnectionPoolSize;
            return this;
        }

        /** 构造EMProperties
         *
         * @return {@code EMProperties}
         */
        public EMProperties build() {
            if (this.baseUri == null) {
                throw new EMInvalidStateException("baseUri not set");
            }
            if (this.appkey == null) {
                throw new EMInvalidStateException("appkey not set");
            }
            if (this.clientId == null) {
                throw new EMInvalidStateException("clientId not set");
            }
            if (this.clientSecret == null) {
                throw new EMInvalidStateException("clientSecret not set");
            }

            return new EMProperties(this.baseUri, this.appkey, this.clientId, this.clientSecret, this.httpConnectionPoolSize);
        }

    }
}