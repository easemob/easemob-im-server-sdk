package com.easemob.im.server.api.room.update;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UpdateRoom {
    
    private Context context;

    public UpdateRoom(Context context) {
        this.context = context;
    }

    public Mono<Void> byId(String id, Consumer<UpdateRoomRequest> customizer) {
        UpdateRoomRequest request = new UpdateRoomRequest();
        customizer.accept(request);

        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                            .uri(String.format("/chatrooms/%s", id))
                            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(request))))
                            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> this.context.getCodec().decode(buf, UpdateRoomResponse.class))
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
