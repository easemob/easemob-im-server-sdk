package com.easemob.im.server.api.metadata.user.delete;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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
                .map(buf -> this.context.getCodec().decode(buf, MetadataDeleteResponse.class))
                .map(MetadataDeleteResponse::getSuccess);
    }

}
