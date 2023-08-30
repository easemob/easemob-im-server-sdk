package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.group.get.GetGroupResponse;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMRoom;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GetRoomDetail {

    private Context context;

    public GetRoomDetail(Context context) {
        this.context = context;
    }

    public Mono<EMRoom> byId(String roomId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatrooms/%s", roomId))
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
                .map(buf -> this.context.getCodec().decode(buf, GetRoomDetailResponse.class)
                        .toRoomDetails().get(0));
    }

    public Mono<List<EMRoom>> byIds(List<String> roomIdList) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatrooms/%s", join(roomIdList, ",")))
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
                .map(buf -> this.context.getCodec().decode(buf, GetRoomDetailResponse.class))
                .map(GetRoomDetailResponse::toRoomDetails);
    }

    public static String join(Collection var0, String var1) {
        StringBuffer var2 = new StringBuffer();

        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if (var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }

}
