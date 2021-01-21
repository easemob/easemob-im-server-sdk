package com.easemob.im.server;

import com.easemob.im.server.api.chatfiles.ChatFilesApi;
import com.easemob.im.server.api.chatgroups.ChatGroupsApi;
import com.easemob.im.server.api.chatmessages.ChatMessagesApi;
import com.easemob.im.server.api.chatrooms.ChatRoomsApi;
import com.easemob.im.server.api.message.MessageApi;
import com.easemob.im.server.api.recallmessage.RecallMessageApi;
import com.easemob.im.server.api.token.TokenApi;
import com.easemob.im.server.api.user.UserApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

public class EMClient {

    private final Cache<String, String> tokenCache;

    private static volatile EMProperties properties;

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    private final ByteBufAllocator allocator;

    private static volatile EMClient instance;

    public EMClient() throws EMClientException {
        //  - cache token
        this.tokenCache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
        //  - BufferAllocator
        this.allocator = PooledByteBufAllocator.DEFAULT;
        //  - ObjectMapper
        this.objectMapper = new ObjectMapper();

        //  - HttpClient

        // 是否要检验ssl
        //        SslContext sslContext = SslContextBuilder
//                .forClient()
//                .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                .build();
//        .secure(t -> {
//            t.sslContext(sslContext);
//        });
        if (properties != null) {
            this.httpClient = HttpClient.create().baseUrl(properties.getBasePath());
        } else {
            throw new EMClientException("Please execute EMClient initializeProperties() method");
        }
    }

    // 单例
    public static EMClient getInstance() throws EMClientException {
        if (instance == null) {
            synchronized (EMClient.class) {
                if (instance == null) {
                    instance = new EMClient();
                }
            }
        }
        return instance;
    }

    // 初始化配置
    public static void initializeProperties(EMProperties property) {
        if (properties == null) {
            synchronized (EMClient.class) {
                if (properties == null) {
                    properties = property;
                }
            }
        }
    }

    // token模块
    public TokenApi token() {
        return new TokenApi(this.tokenCache, properties, this.objectMapper, this.allocator);
    }

    // 用户模块
    public UserApi user() {
        return new UserApi(this.httpClient, this.objectMapper, this.allocator, properties, this.tokenCache);
    }

    // 消息模块
    public MessageApi message() {
        return new MessageApi(this.httpClient, this.objectMapper, this.allocator, properties, this.tokenCache);
    }

    // 群组模块
    public ChatGroupsApi chatGroups() {
        return new ChatGroupsApi(this.httpClient, this.objectMapper, this.allocator, properties, this.tokenCache);
    }

    // 聊天室模块
    public ChatRoomsApi chatRooms() {
        return new ChatRoomsApi(this.httpClient, this.objectMapper, this.allocator, properties, this.tokenCache);
    }

    // 聊天记录模块
    public ChatMessagesApi chatMessages() {
        return new ChatMessagesApi(this.httpClient, this.objectMapper, properties, this.tokenCache);
    }

    // 文件上传下载模块
    public ChatFilesApi chatFiles() {
        return new ChatFilesApi(this.httpClient, this.objectMapper, properties, this.tokenCache);
    }

    // 撤回消息模块
    public RecallMessageApi recall() {
        return new RecallMessageApi(this.httpClient, this.objectMapper, this.allocator, properties, this.tokenCache);
    }
}
