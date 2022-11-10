package com.easemob.im.server.api.metadata.user.usage;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, MetadataUsageResponse.class))
                .map(MetadataUsageResponse::getBytesUsed)
                .map(EMMetadataUsage::new);
    }
}
