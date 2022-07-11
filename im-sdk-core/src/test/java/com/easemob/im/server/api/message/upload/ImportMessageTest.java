package com.easemob.im.server.api.message.upload;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMKeyValue;
import com.easemob.im.server.model.EMTextMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ImportMessageTest extends AbstractApiTest {

    ImportMessage importMessage = new ImportMessage(this.context);

    public ImportMessageTest() {
        this.server.addHandler("POST /easemob/demo/messages/chatgroups/import",
                this::handleSendMessage);
        this.server.addHandler("POST /easemob/demo/messages/users/import",
                this::handleSendMessage);
    }

    private JsonNode handleSendMessage(JsonNode jsonNode) {
        System.out.println("import msg request:");
        System.out.println(jsonNode.toPrettyString());

        ObjectNode messageIdsByReceiverId = this.objectMapper.createObjectNode();
        messageIdsByReceiverId.put("msg_id", ThreadLocalRandom.current().nextLong(2 ^ 63 - 1));

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", messageIdsByReceiverId);
        return rsp;
    }

    @Test
    void testImportChatGroupTextMessage() {
        assertDoesNotThrow(
                () -> this.importMessage.from("alice")
                        .toGroup("186774875865089")
                        .text(msg -> msg.text("hello"))
                        .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                        .isAckRead(true)
                        .msgTimestamp(Instant.now().toEpochMilli())
                        .needDownload(true)
                        .importChatMessage()
                        .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testImportChatImageMessage() {
        assertDoesNotThrow(
                () -> this.importMessage.from("alice")
                        .toGroup("186774875865089")
                        .image(msg -> msg.uri(URI.create("http://example/image.jpg")).height(100.000)
                                .width(200.000))
                        .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                        .isAckRead(true)
                        .msgTimestamp(Instant.now().toEpochMilli())
                        .needDownload(true)
                        .importChatMessage()
                        .block(Utilities.UT_TIMEOUT));
    }
}
