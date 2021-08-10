package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ContactUserTest extends AbstractApiTest {

    private ContactUser contactUser;

    public ContactUserTest() {
        this.server.addHandler("POST /easemob/demo/users/alice/contacts/users/bob",
                this::handleContactUserAdd);
        this.server.addHandler("DELETE /easemob/demo/users/alice/contacts/users/bob",
                this::handleContactUserRemove);
        this.server.addHandler("GET /easemob/demo/users/alice/contacts/users",
                this::handleContactUserList);
        this.contactUser = new ContactUser(this.context);
    }

    @Test
    public void testAddContact() {
        assertDoesNotThrow(() -> this.contactUser.add("alice", "bob").block());
    }

    @Test
    public void testRemoveContact() {
        assertDoesNotThrow(() -> this.contactUser.remove("alice", "bob"));
    }

    @Test
    public void testListContacts() {
        Set<String> contacts = this.contactUser.list("alice").collect(Collectors.toSet())
                .block(Utilities.UT_TIMEOUT);
        assertEquals(3, contacts.size());
        assertTrue(contacts.contains("queen"));
        assertTrue(contacts.contains("madhat"));
        assertTrue(contacts.contains("rabbit"));
    }

    private JsonNode handleContactUserList(JsonNode jsonNode) {
        JsonNode contacts =
                this.objectMapper.createArrayNode().add("queen").add("madhat").add("rabbit");
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
