package com.easemob.im.server.api.util;

import com.easemob.im.server.exception.EMFileSystemException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileSystem {
    public static Path choosePath(Path dir, String filename) {
        if (!dir.toFile().exists()) {
            dir.toFile().mkdirs();
        }
        Path localFile = dir.resolve(filename);
        int suffix = 0;
        while (localFile.toFile().exists()) {
            localFile = dir.resolve(filename+"."+suffix++);
        }
        return localFile;
    }

    public static OutputStream open(Path path) {
        OutputStream outputStream = null;
        try {
            return Files.newOutputStream(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new EMFileSystemException(e.getMessage());
        }
    }

    public static OutputStream append(OutputStream output, ByteBuf buf) {
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

    public static void close(OutputStream out) {
        try {
            out.close();
        } catch (IOException e) {
            throw new EMFileSystemException(e.getMessage());
        }
    }
}
