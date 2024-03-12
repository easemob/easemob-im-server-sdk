package com.easemob.im.server.api.moderation.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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
                    ExportDetailsListResponse response =
                            this.context.getCodec().decode(buf, ExportDetailsListResponse.class);
                    buf.release();
                    return response;
                });
    }
}
