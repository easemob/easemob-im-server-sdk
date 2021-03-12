package com.easemob.im.server.api.chatfiles.download;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMFileSystemException;
import com.sun.nio.file.ExtendedOpenOption;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Download {

    public static Mono<Path> toLocalFile(Context context, String id, Path dir, String filename) {
        Path local = choosePath(dir, filename);
        return Mono.<OutputStream>create(sink -> sink.success(open(local)))
                .flatMap(out -> context.getHttpClient()
                        .get()
                        .uri(String.format("/chatfiles/%s", id))
                        .response((rsp, buf) -> context.getErrorMapper().apply(rsp).thenMany(buf))
                        .doOnNext(buf -> append(out, buf))
                        .doFinally(sig -> close(out))
                        .then())
                .thenReturn(local);
    }

    static Path choosePath(Path dir, String filename) {
        Path localFile = dir.resolve(filename);
        int suffix = 0;
        while (localFile.toFile().exists()) {
            localFile = dir.resolve(filename+"."+suffix++);
        }
        return localFile;
    }

    static OutputStream open(Path path) {
        OutputStream outputStream = null;
        try {
            return Files.newOutputStream(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new EMFileSystemException(e.getMessage());
        }
    }

    static OutputStream append(OutputStream output, ByteBuf buf) {
        byte[] array;
        int offset;
        if (buf.hasArray()) {
            array = buf.array();
            offset = buf.arrayOffset();
        } else {
            array = ByteBufUtil.getBytes(buf);
            offset = 0;
        }

        try {
            output.write(array, offset, buf.readableBytes());
        } catch (IOException e) {
            throw new EMFileSystemException(e.getMessage());
        }

        return output;
    }

    private static void close(OutputStream out) {
        try {
            out.close();
        } catch (IOException e) {
            throw new EMFileSystemException(e.getMessage());
        }
    }
}
