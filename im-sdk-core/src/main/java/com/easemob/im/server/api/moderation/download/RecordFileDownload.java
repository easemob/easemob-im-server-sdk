package com.easemob.im.server.api.moderation.download;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RecordFileDownload {

    private Context context;

    public RecordFileDownload(Context context) {
        this.context = context;
    }

    public Mono<Map> download(String uuid) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/moderationfiles/%s?type=record", uuid))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, Map.class));
    }

}
