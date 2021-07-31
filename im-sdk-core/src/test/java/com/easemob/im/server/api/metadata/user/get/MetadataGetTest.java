package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMBatchMetadata;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MetadataGetTest extends AbstractApiTest {
    MetadataGet metadataGet = new MetadataGet(this.context);

    public MetadataGetTest() {
        this.server.addHandler("GET /easemob/demo/metadata/user/bob", this::handleMetadataGet);
        this.server.addHandler("POST /easemob/demo/metadata/user/get", this::handleMetadataBatchGet);
    }

    @Test
    public void testMetadataGet() {
        assertDoesNotThrow(() -> this.metadataGet.fromUser("bob").block(Duration.ofSeconds(3)));
    }

    @Test
    public void testMetadataBatchGet() {
        EMBatchMetadata batchMetadata =
                assertDoesNotThrow(() -> this.metadataGet.fromUsers(
                        Arrays.asList("alice", "bob"),
                        Arrays.asList("title", "employer", "gender", "name"))
                .block(Duration.ofSeconds(3)));

        Map<String, Map<String, String>> data = batchMetadata.getData();
        Map<String, String> aliceMetadata = data.get("alice");
        Map<String, String> bobMetadata = data.get("bob");
        assertEquals("java_developer", aliceMetadata.get("title"));
        assertEquals("alice wang", aliceMetadata.get("name"));
        assertEquals("easemob", bobMetadata.get("employer"));
        assertEquals("male", bobMetadata.get("gender"));
    }

    private JsonNode handleMetadataGet(JsonNode req) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("nickname", "昵称");
        data.put("avatar", "http://www.easemob.com/avatar.png");
        data.put("phone", "159");

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", data);

        return jsonRsp;
    }

    private JsonNode handleMetadataBatchGet(JsonNode req) {
        ObjectNode aliceMetadata = this.objectMapper.createObjectNode();
        aliceMetadata.put("title", "java_developer");
        aliceMetadata.put("name", "alice wang");
        ObjectNode bobMetadata = this.objectMapper.createObjectNode();
        bobMetadata.put("employer", "easemob");
        bobMetadata.put("gender", "male");
        ObjectNode data = this.objectMapper.createObjectNode();
        data.set("alice", aliceMetadata);
        data.set("bob", bobMetadata);
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", data);
        return jsonRsp;
    }
}
