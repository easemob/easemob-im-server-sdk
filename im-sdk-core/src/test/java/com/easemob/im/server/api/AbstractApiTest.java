package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractApiTest {
    protected ObjectMapper objectMapper;

    protected MockingHttpServer server;

    protected EMProperties properties;

    protected MockingContext context;

    protected AbstractApiTest() {

        this.objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.server = MockingHttpServer.builder().build();

        this.properties = EMProperties.builder()
            .setAppkey("easemob#demo")
            .setClientId("clientId")
            .setClientSecret("clientSecret")
            .build();

        this.context = new MockingContext(properties, this.server.uri());
    }
}
