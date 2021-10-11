package com.easemob.im.server.api.metadata.user.set;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientForm;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MetadataSet {
    private Context context;

    public MetadataSet(Context context) {
        this.context = context;
    }

    public Mono<Void> toUser(String username, Map<String, String> metadata) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/metadata/user/%s", username))
                        .sendForm((req, form) -> {
                            HttpClientForm clientForm =
                                    form.multipart(false).charset(StandardCharsets.UTF_8);
                            metadata.forEach(clientForm::attr);
                        }).responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then());
    }
}
