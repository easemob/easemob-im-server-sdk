package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMPage;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ListRoomMembers {

    private Context context;

    public ListRoomMembers(Context context) {
        this.context = context;
    }

    public Flux<String> all(String roomId, int limit, String sort) {
        return next(roomId, limit, null, sort)
                .expand(rsp -> rsp.getCursor() == null ?
                        Mono.empty() :
                        next(roomId, limit, rsp.getCursor(), sort))
                .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<String>> next(String roomId, int limit, String cursor, String sort) {
        final String uriPath = String.format("/chatrooms/%s/users", roomId);
        QueryStringEncoder encoder = new QueryStringEncoder(uriPath);
        encoder.addParam("version", "v3");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        if (sort != null) {
            encoder.addParam("sort", sort);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
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
                    ListRoomMembersResponse response =
                            this.context.getCodec().decode(buf, ListRoomMembersResponse.class);
                    buf.release();
                    return response;
                })
                .map(ListRoomMembersResponse::toEMPage);
    }

    public Flux<Map<String, String>> all(String roomId, int pageSize) {
        return next(roomId, 1, pageSize)
                .expand(rsp -> {
                    return rsp.getMemberCount() < pageSize ?
                        Mono.empty() :
                        next(roomId,  Integer.parseInt(rsp.getParamsInfo().getPageNum()) + 1, pageSize);
                })
                .concatMapIterable(ListRoomMembersResponseV1::getMembers);
    }

    public Mono<ListRoomMembersResponseV1> next(String roomId, int pageNum, int pageSize) {
        final String uriPath = String.format("/chatrooms/%s/users", roomId);
        QueryStringEncoder encoder = new QueryStringEncoder(uriPath);
        encoder.addParam("pagenum", String.valueOf(pageNum));
        encoder.addParam("pagesize", String.valueOf(pageSize));

        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
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
                    ListRoomMembersResponseV1 response =
                            this.context.getCodec().decode(buf, ListRoomMembersResponseV1.class);
                    buf.release();
                    return response;
                });
    }
}
