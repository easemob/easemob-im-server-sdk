package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

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
            .baseUri(this.server.uri())
            .appkey("easemob#demo")
            .clientId("clientId")
            .clientSecret("clientSecret")
            .build();

        this.context = new MockingContext(properties);
    }
}
