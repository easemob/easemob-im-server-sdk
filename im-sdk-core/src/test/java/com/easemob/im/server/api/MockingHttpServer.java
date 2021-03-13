package com.easemob.im.server.api;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMJsonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class MockingHttpServer {
    private static final ByteBuf EMPTY_OBJECT = Unpooled.wrappedBuffer(new byte[]{'{','}'});

    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final DisposableServer http;

    private Map<String, Function<JsonNode, JsonNode>> handlers;

    private MockingHttpServer(Map<String, Function<JsonNode, JsonNode>> handlers) {
        this.handlers = new ConcurrentHashMap<>(handlers);

        this.http = HttpServer.create()
            .host("localhost")
            .port(0)
            .wiretap(true)
            .handle((req, rsp) -> {
                String path = String.format("%s %s", req.method().toString(), req.uri());
                if (!this.handlers.containsKey(path)) {
                    return rsp.status(HttpResponseStatus.NOT_FOUND).send();
                }

                Function<JsonNode, JsonNode> handler = this.handlers.get(path);
                return req.receive().aggregate()
                    .switchIfEmpty(Mono.just(EMPTY_OBJECT))
                    .flatMap(buf -> {
                        int length = buf.readableBytes();
                        byte[] array;
                        int offset = 0;

                        if (buf.hasArray()) {
                            array = buf.array();
                            offset = buf.arrayOffset();
                        } else {
                            array = ByteBufUtil.getBytes(buf, buf.readerIndex(), buf.readableBytes(), false);
                            offset = 0;
                        }

                        JsonNode jsonReq;
                        try {
                            jsonReq = this.objectMapper.readTree(array, offset, length);
                        } catch (IOException e) {
                            throw new EMJsonException(e.getMessage());
                        }

                        JsonNode jsonRsp = handler.apply(jsonReq);

                        byte[] arrayRsp;
                        try {
                            arrayRsp = this.objectMapper.writeValueAsBytes(jsonRsp);
                        } catch (JsonProcessingException e) {
                            throw new EMJsonException(e.getMessage());
                        }

                        return rsp.status(HttpResponseStatus.OK).send(Mono.create(sink -> sink.success(Unpooled.wrappedBuffer(arrayRsp)))).then();
                    });
            })
            .bindNow();
    }

    public void addHandler(String path, Function<JsonNode, JsonNode> handler) {
        this.handlers.put(path, handler);
    }

    public void removeHandler(String path) {
        this.handlers.remove(path);
    }

    public String uri() {
        return String.format("http://localhost:%s", this.http.port());
    }

    public int port() {
        return this.http.port();
    }

    public static Builder builder() {
        return new Builder();
    }

    public void shutdown() {
        this.http.disposeNow();
    }

    public static class Builder {
        private Map<String, Function<JsonNode, JsonNode>> postHandlers = new HashMap<>();

        public Builder addHandler(String path, Function<JsonNode, JsonNode> handler) {
            if (path == null || path.isEmpty()) {
                throw new EMInvalidArgumentException("path must not be null or empty");
            }
            if (handler == null) {
                throw new EMInvalidStateException("handler must not be null");
            }

            this.postHandlers.put(path, handler);
            return this;
        }

        public MockingHttpServer build() {
            return new MockingHttpServer(this.postHandlers);
        }
    }
}
