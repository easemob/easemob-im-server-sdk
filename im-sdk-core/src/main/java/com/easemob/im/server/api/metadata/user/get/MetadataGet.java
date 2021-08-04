package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMMetadata;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class MetadataGet {
    private Context context;

    public MetadataGet(Context context) {
        this.context = context;
    }

    public Mono<EMMetadata> fromUser(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/metadata/user/%s", username))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, MetadataGetUserResponse.class))
                .map(MetadataGetUserResponse::toMetadata);
    }

    public Mono<Map<String, EMMetadata>> fromUsers(List<String> userNames, List<String> propertyNames) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(headers -> headers.set("Content-Type", "application/json"))
                        .post()
                        .uri("/metadata/user/get")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new MetadataGetUsersRequest(userNames, propertyNames)))))
                        .responseSingle(
                                (rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                )
                .map(buf -> this.context.getCodec().decode(buf, MetadataGetUsersResponse.class))
                .map(MetadataGetUsersResponse::toMetadataMap);
    }
}
