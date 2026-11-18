package com.easemob.im.api;

import com.easemob.im.ApiClient;
import com.easemob.im.JSON;
import com.easemob.im.api.model.EMBatchRecallMessageResource;
import com.easemob.im.api.model.EMBatchRecallMessagesResult;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatchRecallMessagesResultTest {
    private static final String MESSAGES = "["
            + "{\"msg_id\":\"message-1\",\"recalled\":\"yes\",\"from\":\"sender\",\"to\":\"receiver\",\"chattype\":\"chat\"},"
            + "{\"msg_id\":\"message-2\",\"recalled\":\"no\",\"from\":\"sender\",\"to\":\"receiver\",\"chattype\":\"chat\"}"
            + "]";

    @BeforeEach
    void initializeJson() {
        new JSON();
    }

    @Test
    void parsesWrappedDataThroughHttpResponseHandling() throws Exception {
        String body = "{\"path\":\"/messages/batch_recall\",\"timestamp\":123,\"data\":{\"msgs\":"
                + MESSAGES + "}}";
        try (Response response = new Response.Builder()
                .request(new Request.Builder().url("https://api.example.com/messages/batch_recall").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(body, MediaType.parse("application/json")))
                .build()) {
            EMBatchRecallMessagesResult result = new ApiClient().handleResponse(
                    response, EMBatchRecallMessagesResult.class);
            assertMessages(result);
        }
    }

    @Test
    void parsesLegacyArrayData() throws Exception {
        assertMessages(EMBatchRecallMessagesResult.fromJson("{\"data\":" + MESSAGES + "}"));
    }

    @Test
    void parsesWrappedDataFromJson() throws Exception {
        assertMessages(EMBatchRecallMessagesResult.fromJson("{\"data\":{\"msgs\":" + MESSAGES + "}}"));
    }

    @Test
    void parsesEmptyWrappedMessages() throws Exception {
        assertTrue(EMBatchRecallMessagesResult.fromJson("{\"data\":{\"msgs\":[]}}").getData().isEmpty());
    }

    @Test
    void parsesEmptyLegacyMessages() throws Exception {
        assertTrue(EMBatchRecallMessagesResult.fromJson("{\"data\":[]}").getData().isEmpty());
    }

    @Test
    void preservesNullData() throws Exception {
        assertNull(EMBatchRecallMessagesResult.fromJson("{\"data\":null}").getData());
    }

    @Test
    void preservesNullWrappedMessages() throws Exception {
        assertNull(EMBatchRecallMessagesResult.fromJson("{\"data\":{\"msgs\":null}}").getData());
    }

    @Test
    void preservesEmptyDefaultWhenDataIsMissing() throws Exception {
        assertTrue(EMBatchRecallMessagesResult.fromJson("{}").getData().isEmpty());
    }

    @Test
    void rejectsWrappedDataWithoutMessages() {
        assertThrows(JsonParseException.class,
                () -> EMBatchRecallMessagesResult.fromJson("{\"data\":{}}"));
    }

    @Test
    void rejectsNonArrayWrappedMessages() {
        assertThrows(JsonParseException.class,
                () -> EMBatchRecallMessagesResult.fromJson("{\"data\":{\"msgs\":{}}}"));
    }

    @Test
    void rejectsNonArrayData() {
        assertThrows(JsonParseException.class,
                () -> EMBatchRecallMessagesResult.fromJson("{\"data\":\"invalid\"}"));
    }

    @Test
    void preservesLegacySerialization() throws Exception {
        EMBatchRecallMessagesResult result = new EMBatchRecallMessagesResult()
                .data(Collections.singletonList(new EMBatchRecallMessageResource()
                        .msgId("message-1").recalled("yes")));

        assertEquals(JsonParser.parseString("{\"data\":[{\"msg_id\":\"message-1\",\"recalled\":\"yes\"}]}"),
                JsonParser.parseString(result.toJson()));
        assertEquals(result, EMBatchRecallMessagesResult.fromJson(result.toJson()));
    }

    @Test
    void parsesWrappedDataWithGeneratedFactory() {
        Gson gson = JSON.createGson()
                .registerTypeAdapterFactory(new EMBatchRecallMessagesResult.CustomTypeAdapterFactory())
                .create();

        assertMessages(gson.fromJson("{\"data\":{\"msgs\":" + MESSAGES + "}}",
                EMBatchRecallMessagesResult.class));
    }

    private static void assertMessages(EMBatchRecallMessagesResult result) {
        assertNotNull(result);
        assertNotNull(result.getData());
        assertEquals(2, result.getData().size());
        assertEquals("message-1", result.getData().get(0).getMsgId());
        assertEquals("yes", result.getData().get(0).getRecalled());
        assertEquals("sender", result.getData().get(0).getFrom());
        assertEquals("receiver", result.getData().get(0).getTo());
        assertEquals("chat", result.getData().get(0).getChattype());
        assertEquals("message-2", result.getData().get(1).getMsgId());
        assertEquals("no", result.getData().get(1).getRecalled());
    }
}
