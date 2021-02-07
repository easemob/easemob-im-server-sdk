package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactUserTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("POST /easemob/demo/users/alice/contacts/users/bob", this::handleContactUserAdd)
        .addHandler("DELETE /easemob/demo/users/alice/contacts/users/bob", this::handleContactUserRemove)
        .addHandler("GET /easemob/demo/users/alice/contacts/users", this::handleContactUserList)
        .build();

    private EMProperties properties = EMProperties.builder()
        .setBaseUri(this.server.uri())
        .setAppkey("easemob#demo")
        .setClientId("clientId")
        .setClientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testAddContact() {
        ContactUser contactUser = new ContactUser(this.context, "alice");
        assertDoesNotThrow(() -> contactUser.add("bob").block());
    }

    @Test
    public void testRemoveContact() {
        ContactUser contactUser = new ContactUser(this.context, "alice");
        assertDoesNotThrow(() -> contactUser.remove("bob"));
    }

    @Test
    public void testListContacts() {
        ContactUser contactUser = new ContactUser(this.context, "alice");
        List<String> contacts = contactUser.list().collectList().block(Duration.ofSeconds(3));
        assertEquals(3, contacts.size());
        assertTrue(contacts.contains("queen"));
        assertTrue(contacts.contains("madhat"));
        assertTrue(contacts.contains("rabbit"));
    }

    private JsonNode handleContactUserList(JsonNode jsonNode) {
        JsonNode contacts = this.objectMapper.createArrayNode().add("queen").add("madhat").add("rabbit");
        JsonNode response = this.objectMapper.createObjectNode().set("data", contacts);
        return response;
    }

    private JsonNode handleContactUserRemove(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleContactUserAdd(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}