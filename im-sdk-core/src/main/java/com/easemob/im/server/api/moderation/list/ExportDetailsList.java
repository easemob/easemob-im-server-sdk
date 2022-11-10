package com.easemob.im.server.api.moderation.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
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
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, ExportDetailsListResponse.class));
    }
}
