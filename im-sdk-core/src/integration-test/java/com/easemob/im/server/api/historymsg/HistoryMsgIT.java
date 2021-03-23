package com.easemob.im.server.api.historymsg;

import com.easemob.im.server.api.AbstractIT;
import com.sun.nio.zipfs.ZipPath;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassLoaderUtils;
import sun.misc.ClassLoaderUtil;
import sun.misc.Resource;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HistoryMsgIT extends AbstractIT {

    HistoryMsgIT() {
        super();
    }

    @Test
    void testHistoryMsgGetAsUri() {
        assertDoesNotThrow(() -> this.service.message().getHistoryAsUri(Instant.ofEpochSecond(1616407200)).block(Duration.ofSeconds(3)));
    }

    @Test
    void testHistoryMsgGetAsLocalFile() {
//        Path path = FileSystems.getDefault().getPath("/../history");
        ResourceBundle bundle = ResourceBundle.getBundle("/upload");
//        bundle.getBaseBundleName();

        System.out.println("kkk = " + bundle.getBaseBundleName());
//        assertDoesNotThrow(() -> this.service.message().getHistoryAsLocalFile(Instant.ofEpochSecond(1616407200), path, "history.gz").block(Duration.ofSeconds(3)));
    }

}
