package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.model.EMMetadataBatch;
import reactor.core.publisher.Mono;

import java.util.List;

public class MetadataBatchGet {
    private Context context;

    public MetadataBatchGet(Context context) {
        this.context = context;
    }

    public Mono<EMMetadataBatch> fromUsers(List<String> targets, List<String> properties) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.headers(header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri("/metadata/user/get")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new MetadataBatchGetRequest(targets, properties)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, MetadataBatchGetResponse.class))
                .map(MetadataBatchGetResponse::toMetadataBatch);
    }
}
