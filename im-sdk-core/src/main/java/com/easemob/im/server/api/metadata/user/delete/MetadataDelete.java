package com.easemob.im.server.api.metadata.user.delete;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class MetadataDelete {
    private Context context;

    public MetadataDelete(Context context) {
        this.context = context;
    }

    public Mono<Boolean> fromUser(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/metadata/user/%s", username))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, MetadataDeleteResponse.class))
                .map(MetadataDeleteResponse::getSuccess);
    }

}
