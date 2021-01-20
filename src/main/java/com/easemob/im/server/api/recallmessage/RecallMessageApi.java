package com.easemob.im.server.api.recallmessage;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.recallmessage.exception.RecallMessageException;
import com.easemob.im.server.model.RecallMessage;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

import java.util.Set;

public class RecallMessageApi {

    private final HttpClient http;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    private final EMProperties properties;

    private final Cache<String, String> tokenCache;

    public RecallMessageApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator, EMProperties properties, Cache<String, String> tokenCache) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
        this.properties = properties;
        this.tokenCache = tokenCache;
    }

    /**
     * 消息撤回
     *
     * 撤回单条消息，可以撤回已经发送出去的消息，接收消息的用户将看不到此条消息
     *
     * 由于该功能是增值功能，确保使用前已经开通此功能
     *
     * @param messageId  需要撤回的消息id
     * @param to         接收消息方（用户id或者群组id）
     * @param type       消息类型（单聊或者群组消息）
     * @return JsonNode
     */
    public JsonNode recallMessage(String messageId, String to, ChatType type) {
        verifyMessageId(messageId);
        verifyTo(to);

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("to", to);
        msg.put("msg_id", messageId);
        msg.put("chat_type", String.valueOf(type));

        ArrayNode msgArray = this.mapper.createArrayNode();
        msgArray.add(msg);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("msgs", msgArray);

        return HttpUtils.execute(this.http, HttpMethod.POST, "/messages/recall", request, this.allocator, this.mapper, this.properties, this.tokenCache);
    }

    /**
     * 消息撤回
     *
     * 撤回多条消息
     *
     * @param messages 需要撤回多条消息的集合
     * @return  JsonNode
     */
    public JsonNode recallMessage(Set<RecallMessage> messages) {
        verifyMessage(messages);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("msgs", this.mapper.valueToTree(messages));

        return HttpUtils.execute(this.http, HttpMethod.POST, "/messages/recall", request, this.allocator, this.mapper, this.properties, this.tokenCache);
    }

    private void verifyMessage(Set<RecallMessage> messages) {
        if (messages == null || messages.size() < 1) {
            throw new RecallMessageException("Bad Request invalid messages");
        }
    }

    private void verifyMessageId(String messageId) {
        if (messageId == null || messageId.isEmpty()) {
            throw new RecallMessageException("Bad Request invalid messageId");
        }
    }

    private void verifyTo(String to) {
        if (to == null || to.isEmpty()) {
            throw new RecallMessageException("Bad Request invalid to");
        }
    }

}
