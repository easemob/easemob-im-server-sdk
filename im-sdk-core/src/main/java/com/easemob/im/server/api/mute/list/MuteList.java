package com.easemob.im.server.api.mute.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.token.allocate.AgoraTokenProvider;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMMute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MuteList {
    private static final Logger log = LoggerFactory.getLogger(MuteList.class);

    private Context context;

    public MuteList(Context context) {
        this.context = context;
    }

    public Mono<GetMuteListResponse> execute() {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri("/mutes")
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
                    GetMuteListResponse response =
                            this.context.getCodec().decode(buf, GetMuteListResponse.class);
                    buf.release();
                    return response;
                });
    }
}
