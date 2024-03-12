package com.easemob.im.server.api.metadata.user.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMMetadata;
import reactor.core.publisher.Mono;

public class MetadataGet {
    private Context context;

    public MetadataGet(Context context) {
        this.context = context;
    }

    public Mono<EMMetadata> fromUser(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/metadata/user/%s", username))
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
                    MetadataGetUserResponse response =
                            this.context.getCodec().decode(buf, MetadataGetUserResponse.class);
                    buf.release();
                    return response;
                })
                .map(MetadataGetUserResponse::toMetadata);
    }
}
