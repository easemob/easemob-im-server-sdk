package com.easemob.im.server;

import com.easemob.im.server.api.ApiException;
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

    public EMClient() {
        // TODO: 在这里实例化：

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
        if (this.properties != null) {
            this.httpClient = HttpClient.create().baseUrl(this.properties.getBasePath());
        } else {
            throw new ApiException("Please execute EMClient instance() method");
        }
    }

    // 单例
    public static EMClient getInstance() {
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
    public static void instance(EMProperties property) {
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
        return new TokenApi(this.tokenCache, this.properties, this.objectMapper, this.allocator);
    }

    // 用户模块
    public UserApi user() {
        return new UserApi(this.httpClient, this.objectMapper, this.allocator, this.properties, this.tokenCache);
    }

    // 消息模块
    public MessageApi message() {
        return new MessageApi(this.httpClient, this.objectMapper, this.allocator, this.properties, this.tokenCache);
    }

    // 群组模块
    public ChatGroupsApi chatGroups() {
        return new ChatGroupsApi(this.httpClient, this.objectMapper, this.allocator, this.properties, this.tokenCache);
    }

    // 聊天室模块
    public ChatRoomsApi chatRooms() {
        return new ChatRoomsApi(this.httpClient, this.objectMapper, this.allocator, this.properties, this.tokenCache);
    }

    // 聊天记录模块
    public ChatMessagesApi chatMessages() {
        return new ChatMessagesApi(this.httpClient, this.objectMapper, this.properties, this.tokenCache);
    }

    // 文件上传下载模块
    public ChatFilesApi chatFiles() {
        return new ChatFilesApi(this.httpClient, this.objectMapper, this.properties, this.tokenCache);
    }

    // 撤回消息模块
    public RecallMessageApi recall() {
        return new RecallMessageApi(this.httpClient, this.objectMapper, this.allocator, this.properties, this.tokenCache);
    }
}
