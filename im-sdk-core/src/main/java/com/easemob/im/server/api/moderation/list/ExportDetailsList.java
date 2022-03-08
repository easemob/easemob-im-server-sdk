package com.easemob.im.server.api.moderation.list;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

public class ExportDetailsList {

    private Context context;

    public ExportDetailsList(Context context) {
        this.context = context;
    }

    public Mono<ExportDetailsListResponse> get(int page, int pageSize, String uuid) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/moderation/export/files?page=%d&size=%d&uuid=%s", page, pageSize, uuid))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ExportDetailsListResponse.class));
    }
}
