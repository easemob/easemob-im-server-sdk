package com.easemob.im.server.api.room.announcement;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import reactor.core.publisher.Mono;

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
                        .responseSingle((rsp, buf) -> {
                            this.context.getErrorMapper().statusCode(rsp);
                            return buf;
                        })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .then();
    }
}

