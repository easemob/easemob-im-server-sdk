package com.easemob.im.server.api.metadata.user.capacity;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class MetadataCapacity {
    private Context context;

    public MetadataCapacity(Context context) {
        this.context = context;
    }

    public Mono<Long> getCapacity() {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri("/metadata/user/capacity")
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                        .map(buf -> this.context.getCodec().decode(buf, MetadataCapacityResponse.class))
                        .map(MetadataCapacityResponse::getData));
    }
}
