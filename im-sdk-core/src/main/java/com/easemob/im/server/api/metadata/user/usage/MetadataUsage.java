package com.easemob.im.server.api.metadata.user.usage;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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
                        .responseSingle((rsp, buf) -> {
                            return buf.switchIfEmpty(
                                            Mono.error(new EMUnknownException("response is null")))
                                    .flatMap(byteBuf -> {
                                        ErrorMapper mapper = new DefaultErrorMapper();
                                        mapper.statusCode(rsp);
                                        mapper.checkError(byteBuf);
                                        return Mono.just(byteBuf);
                                    });
                        }))
                .map(buf -> {
                    MetadataUsageResponse response =
                            this.context.getCodec().decode(buf, MetadataUsageResponse.class);
                    buf.release();
                    return response;
                })
                .map(MetadataUsageResponse::getBytesUsed)
                .map(EMMetadataUsage::new);
    }
}
