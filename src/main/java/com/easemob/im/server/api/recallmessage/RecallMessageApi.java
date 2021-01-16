package com.easemob.im.server.api.recallmessage;

import com.easemob.im.server.api.recallmessage.exception.RecallMessageException;
import com.easemob.im.server.model.RecallMessage;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;

import java.util.Set;
import java.util.regex.Pattern;

public class RecallMessageApi {

    private final HttpClient http;

    private final ObjectMapper mapper;

    private final ByteBufAllocator allocator;

    public RecallMessageApi(HttpClient http, ObjectMapper mapper, ByteBufAllocator allocator) {
        this.http = http;
        this.mapper = mapper;
        this.allocator = allocator;
    }

    public JsonNode recallMessage(String messageId, String to, ChatType type) {
        verifyMessageId(messageId);
        verifyTo(to);

        ObjectNode msg = this.mapper.createObjectNode();
        msg.put("msg_id", messageId);
        msg.put("to", to);
        msg.put("chat_type", String.valueOf(type.chat));

        ArrayNode msgArray = this.mapper.createArrayNode();
        msgArray.add(msg);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("msg", msgArray);

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, "/messages/recall", request, this.allocator, this.mapper);
        return response;
    }

    public JsonNode recallMessage(Set<RecallMessage> messages) {
        verifyMessage(messages);

        ObjectNode request = this.mapper.createObjectNode();
        request.set("msg", this.mapper.valueToTree(messages));

        JsonNode response = HttpUtils.execute(this.http, HttpMethod.POST, "/messages/recall", request, this.allocator, this.mapper);
        return response;
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
