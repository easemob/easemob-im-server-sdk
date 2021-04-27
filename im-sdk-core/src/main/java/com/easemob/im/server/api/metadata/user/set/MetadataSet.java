package com.easemob.im.server.api.metadata.user.set;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientForm;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetadataSet {
    private Context context;

    public MetadataSet(Context context) {
        this.context = context;
    }

    public Mono<MetadataSetResponse> set(String username, Map<String, String> metadata) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.headersWhen(headers -> Mono.just(headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")))
                        .put()
                        .uri(String.format("/metadata/user/%s", username))
                        .sendForm((req, form) -> {
                            HttpClientForm clientForm = form.multipart(false).charset(StandardCharsets.UTF_8);
                            Set<String> keySet = metadata.keySet();
                            List<String> keyList = new ArrayList<>(keySet);
                            for (int i = 0; i < metadata.size(); i ++) {
                                clientForm.attr(keyList.get(i), metadata.get(keyList.get(i)));
                            }
                        })
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, MetadataSetResponse.class)));
    }
}
