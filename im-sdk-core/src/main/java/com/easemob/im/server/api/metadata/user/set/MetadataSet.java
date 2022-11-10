package com.easemob.im.server.api.metadata.user.set;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .then();
    }
}
