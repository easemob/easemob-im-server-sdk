package com.easemob.im.server.api.metadata.user.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, MetadataDeleteResponse.class))
                .map(MetadataDeleteResponse::getSuccess);
    }

}
