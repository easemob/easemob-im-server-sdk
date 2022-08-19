package com.easemob.im.server.api.room.announcement;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public class RoomAnnouncement {

    private Context context;

    public RoomAnnouncement(Context context) {
        this.context = context;
    }

    public Mono<String> get(String roomId) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatrooms/%s/announcement", roomId))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, RoomAnnouncementGetResponse.class))
                .map(RoomAnnouncementGetResponse::getAnnouncement);
    }

    public Mono<Void> set(String groupId, String announcement) {
        if (announcement.length() > 512) {
            return Mono.error(new EMInvalidArgumentException(
                    "announcement must not be longer than 512 characters"));
        }
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatrooms/%s/announcement", groupId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new RoomAnnouncementResource(announcement)))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                        .flatMap(tuple2 -> {
                            HttpClientResponse clientResponse = tuple2.getT1();

                            return Mono.defer(() -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(clientResponse);
                                mapper.checkError(tuple2.getT2());
                                return Mono.just(tuple2.getT2());
                            }).onErrorResume(e -> {
                                if (e instanceof EMException) {
                                    return Mono.error(e);
                                }
                                return Mono.error(new EMUnknownException(
                                        String.format("roomId:%s,announcement:%s", groupId,
                                                announcement)));
                            }).then();
                        }));
    }
}

