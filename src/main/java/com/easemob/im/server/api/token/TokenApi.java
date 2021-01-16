package com.easemob.im.server.api.token;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.token.exception.TokenException;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

public class TokenApi {

    private final Cache<String, String> tokenCache;

    private final EMProperties properties;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    public TokenApi(Cache<String, String> tokenCache, EMProperties properties, ObjectMapper mapper, ByteBufAllocator allocator) {
        this.tokenCache = tokenCache;
        this.properties = properties;
        this.mapper = mapper;
        this.allocator = allocator;
    }

    /**
     * 获取管理员权限
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%AE%A1%E7%90%86%E5%91%98%E6%9D%83%E9%99%90
     *
     * 环信提供的 REST API 需要权限才能访问，权限通过发送 HTTP 请求时携带 token 来体现，下面描述获取 token 的方式。
     * 说明：API 描述的时候使用到的 {APP 的 client_id} 之类的这种参数需要替换成具体的值。
     *
     * @return  token
     */
    public String getToken() {
        String key = this.properties.getAppKey();

        if (this.properties.getClientId() == null || this.properties.getClientId().isEmpty()) {
            throw new TokenException("invalid clientId");
        }
        if (this.properties.getClientSecret() == null || this.properties.getClientSecret().isEmpty()) {
            throw new TokenException("invalid clientSecret");
        }

        ObjectNode request = this.mapper.createObjectNode();
        request.put("grant_type", "client_credentials");
        request.put("client_id", this.properties.getClientId());
        request.put("client_secret", this.properties.getClientSecret());

        HttpClient httpClient = HttpClient.create()
                .baseUrl(this.properties.getBasePath());
        JsonNode result = HttpUtils.execute(httpClient, HttpMethod.POST, "/token", request, this.allocator, this.mapper);
        if (result == null) {throw new TokenException("get token result is null");}

        String accessToken = result.get("access_token").asText();
        if (accessToken != null) {
            this.tokenCache.put(key, accessToken);
            return accessToken;
        } else {
            throw new TokenException("access_token is null");
        }
    }

}
