package com.easemob.im.server.api.moderation.export;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.user.create.CreateUserRequest;
import com.easemob.im.server.api.user.get.UserGetResponse;
import reactor.core.publisher.Mono;

public class ExportMessageRecord {

    private Context context;

    public ExportMessageRecord(Context context) {
        this.context = context;
    }

    public Mono<String> export(Long beginTimestamp, Long endTimestamp,
            String targetType, String messageType, String moderationResult, String providerResult) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.headers(
                                header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri("/moderation/record/message/export")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ExportMessageRecordRequest(beginTimestamp, endTimestamp,
                                        targetType, messageType, moderationResult,
                                        providerResult)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, ExportMessageRecordResponse.class)
                        .getFileUuid());
    }
}
