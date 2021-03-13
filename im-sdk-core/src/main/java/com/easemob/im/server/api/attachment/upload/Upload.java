package com.easemob.im.server.api.attachment.upload;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public class Upload {

    public static Mono<String> fromLocalFile(Context context, Path path, boolean restrictAccess) {
        return context.getHttpClient()
                .headers(headers -> headers.add("restrict-access", false))
                //.headers(headers -> headers.remove("Authorization"))
                .post()
                .uri("/chatfiles")
                .sendForm((req, form) -> {
                    form.multipart(true)
                            .attr("filename", path.getFileName().toString())
                            .file("file", path.toFile());
                }).responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, UploadFileResponse.class))
                .handle((rsp, sink) -> {
                    if (rsp.getFiles().isEmpty()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    sink.next(rsp.getFiles().get(0).getId());
                });
    }


}
