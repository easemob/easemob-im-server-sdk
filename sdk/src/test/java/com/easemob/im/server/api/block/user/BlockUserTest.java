package com.easemob.im.server.api.block.user;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockUserTest extends AbstractApiTest {

    public BlockUserTest() {
        super();
        this.server.addHandler("POST /easemob/demo/users/alice/blocks/users", this::handleUserBlockFromSendMsgRequest);
        this.server.addHandler("DELETE /easemob/demo/users/alice/blocks/users", this::handleUserUnblockFromSendMsgRequest);
        this.server.addHandler("GET /easemob/demo/users/alice/blocks/users", this::handleGetUserBlockedFromSendMsgRequest);
        this.server.addHandler("POST /easemob/demo/users/alice/deactivate", this::handleUserBlockFromLoginRequest);
        this.server.addHandler("POST /easemob/demo/users/alice/activate", this::handleUserUnblockFromLoginRequest);
    }

    @Test
    public void testBlockUserFromSendMsg() {
        BlockUser blockUser = new BlockUser(this.context, Flux.just("queen", "madhat", "rabbit"));

        Flux.from(blockUser.fromSendMessageToUser("alice"))
            .as(StepVerifier::create)
            .expectNext("queen")
            .expectNext("madhat")
            .expectNext("rabbit")
            .expectComplete().verify(Duration.ofSeconds(3));
    }

    @Test
    public void testUnBlockUserFromSendMsg() {
        UnblockUser unblockUser = new UnblockUser(this.context, Flux.just("queen", "madhat", "rabbit"));
        Flux.from(unblockUser.fromSendMessageToUser("alice"))
            .as(StepVerifier::create)
            .expectNext("queen")
            .expectNext("madhat")
            .expectNext("rabbit")
            .expectComplete().verify(Duration.ofSeconds(3));
    }

    @Test
    public void testGetUserBlockedFromSendMsg() {
        GetUserBlocked getUserBlocked = new GetUserBlocked(this.context);
        List<String> blocked = getUserBlocked.fromSendingMessagesTo("alice").collectList().block(Duration.ofSeconds(3));
        assertEquals(3, blocked.size());
        assertTrue(blocked.contains("queen"));
        assertTrue(blocked.contains("madhat"));
        assertTrue(blocked.contains("rabbit"));
    }

    @Test
    public void testBlockUserFromLogin() {
        BlockUser blockUser = new BlockUser(this.context, Flux.just("alice"));

        Mono.from(blockUser.fromLogin())
            .as(StepVerifier::create)
            .expectNext("alice")
            .expectComplete()
            .verify(Duration.ofSeconds(3));
    }

    @Test
    public void testUnblockUserFromLogin() {
        UnblockUser unblockUser = new UnblockUser(this.context, Flux.just("alice"));
        Mono.from(unblockUser.fromLogin())
            .as(StepVerifier::create)
            .expectNext("alice")
            .expectComplete()
            .verify(Duration.ofSeconds(3));
    }

    private JsonNode handleGetUserBlockedFromSendMsgRequest(JsonNode jsonNode) {
        JsonNode usernames = this.objectMapper.createArrayNode().add("queen").add("madhat").add("rabbit");
        JsonNode response = this.objectMapper.createObjectNode().set("data", usernames);
        return response;
    }

    private JsonNode handleUserUnblockFromSendMsgRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleUserBlockFromSendMsgRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleUserBlockFromLoginRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleUserUnblockFromLoginRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}