package com.easemob.im.server.api.sms.send;

import com.easemob.im.server.api.Context;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

public class SendSms {
    private final Context context;

    public SendSms(Context context) {
        this.context = context;
    }

    public Mono<Integer> send(Set<String> phoneNumbers, String smsTemplateId, Map<String, String> templateVariableContent, String extendCode, String custom) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.headersWhen(headers -> Mono.just(headers.add("Content-Type", "application/json")))
                        .post()
                        .uri("/sms/send")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new SendSmsRequest(phoneNumbers, smsTemplateId, templateVariableContent, extendCode, custom)))))
                        .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, SendSmsResponse.class))
                .map(SendSmsResponse::getCount);
    }
}
