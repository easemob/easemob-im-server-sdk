package com.easemob.im.server.api.attachment.download;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.util.FileSystem;
import com.easemob.im.server.exception.EMFileSystemException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Download {

    private Context context;

    public Download(Context context) {
        this.context = context;
    }

    public Mono<Path> toLocalFile(String id, Path dir, String filename) {
        Path local = FileSystem.choosePath(dir, filename);
        return Mono.<OutputStream>create(sink -> sink.success(FileSystem.open(local)))
                .flatMap(out -> this.context.getHttpClient()
                        .get()
                        .uri(String.format("/chatfiles/%s", id))
                        .response((rsp, buf) -> this.context.getErrorMapper().apply(rsp).thenMany(buf))
                        .doOnNext(buf -> FileSystem.append(out, buf))
                        .doFinally(sig -> FileSystem.close(out))
                        .then())
                .thenReturn(local);
    }


}
