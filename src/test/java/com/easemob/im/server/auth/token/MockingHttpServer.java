package com.easemob.im.server.auth.token;

import org.reactivestreams.Publisher;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.util.function.BiFunction;

public class MockingHttpServer {

    private DisposableServer http;

    public MockingHttpServer(BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> handler) {
        this.http = HttpServer.create()
            .host("localhost")
            .port(0)
            .handle(handler)
            .bindNow();
    }

    public String uri() {
        return String.format("http://localhost:%s", this.http.port());
    }

    public void shutdown() {
        this.http.disposeNow();
    }

}
