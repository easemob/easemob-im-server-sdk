package com.easemob.im.server.api.room.update;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UpdateRoom {

    public static Mono<Void> byId(Context context, String id, Consumer<UpdateRoomRequest> customizer) {
        UpdateRoomRequest request = new UpdateRoomRequest();
        customizer.accept(request);

        return context.getHttpClient()
                .put()
                .uri(String.format("/chatrooms/%s", id))
                .send(Mono.create(sink -> sink.success(context.getCodec().encode(request))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, UpdateRoomResponse.class))
                .handle((rsp, sink) -> {
                    List<String> notUpdated = new ArrayList<>();
                    if (request.hasName() && !rsp.nameUpdated()) {
                        notUpdated.add("name");
                    }
                    if (request.hasDescription() && !rsp.descriptionUpdated()) {
                        notUpdated.add("description");
                    }
                    if (request.hasMaxMembers() && !rsp.maxMembersUpdated()) {
                        notUpdated.add("maxMembers");
                    }
                    if (!notUpdated.isEmpty()) {
                        sink.error(new EMUnknownException(String.format("%s not updated", String.join(",", notUpdated))));
                        return;
                    }
                    sink.complete();
                });
    }
}
