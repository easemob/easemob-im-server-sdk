package com.easemob.im.server.api.attachment.upload;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public class Upload {
    
    private Context context;

    public Upload(Context context) {
        this.context = context;
    }

    public Mono<String> fromLocalFile(Path path, boolean restrictAccess) {
        return this.context.getHttpClient()
                .headers(headers -> headers.add("restrict-access", false))
                //.headers(headers -> headers.remove("Authorization"))
                .post()
                .uri("/chatfiles")
                .sendForm((req, form) -> {
                    form.multipart(true)
                            .attr("filename", path.getFileName().toString())
                            .file("file", path.toFile());
                }).responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> this.context.getCodec().decode(buf, UploadFileResponse.class))
                .handle((rsp, sink) -> {
                    if (rsp.getFiles().isEmpty()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.next(rsp.getFiles().get(0).getId());
                });
    }


}
