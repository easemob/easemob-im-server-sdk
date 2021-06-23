package com.easemob.im.server.api.metadata.user.usage;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMMetadataUsage;
import reactor.core.publisher.Mono;

public class MetadataUsage {
    private Context context;

    public MetadataUsage(Context context) {
        this.context = context;
    }

    public Mono<EMMetadataUsage> getUsage() {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri("/metadata/user/capacity")
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, MetadataUsageResponse.class))
                .map(MetadataUsageResponse::getBytesUsed)
                .map(EMMetadataUsage::new);
    }
}
