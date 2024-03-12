package com.easemob.im.server.api.metadata.user.set;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientForm;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MetadataSet {
    private Context context;

    public MetadataSet(Context context) {
        this.context = context;
    }

    public Mono<Void> toUser(String username, Map<String, String> metadata) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/metadata/user/%s", username))
                        .sendForm((req, form) -> {
                            HttpClientForm clientForm =
                                    form.multipart(false).charset(StandardCharsets.UTF_8);
                            metadata.forEach(clientForm::attr);
                        })
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
                .doOnSuccess(ReferenceCounted::release)
                .then();
    }
}
