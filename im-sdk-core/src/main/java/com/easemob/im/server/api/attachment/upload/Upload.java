package com.easemob.im.server.api.attachment.upload;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMAttachment;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public class Upload {

    private Context context;

    public Upload(Context context) {
        this.context = context;
    }

    public Mono<EMAttachment> fromLocalFile(Path path, boolean restrictAccess) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient
                        .headers(headers -> headers.add("restrict-access", restrictAccess))
                        .post()
                        .uri("/chatfiles")
                        .sendForm((req, form) -> form.multipart(true)
                                .attr("filename", path.getFileName().toString())
                                .file("file", path.toFile())).responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, UploadFileResponse.class))
                .handle((rsp, sink) -> {
                    if (rsp.getFiles().isEmpty()) {
                        sink.error(new EMUnknownException("unknown"));
                        return;
                    }
                    String id = rsp.getFiles().get(0).getId();
                    String url = rsp.getBaseUrl() + "/" + id;
                    String secret = rsp.getFiles().get(0).getSecret();
                    sink.next(new EMAttachment(id, url, secret));
                });
    }
}
