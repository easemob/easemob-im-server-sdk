package com.easemob.im.server.api.historymsg;

import com.easemob.im.server.api.AbstractIT;
import org.junit.jupiter.api.Test;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HistoryMsgIT extends AbstractIT {

    HistoryMsgIT() {
        super();
    }

    @Test
    void testHistoryMsgGetAsUri() {
//        assertDoesNotThrow(() -> this.service.message().getHistoryAsUri(Instant.ofEpochSecond(1618567200)).block(Duration.ofSeconds(3)));
        Instant now = Instant.parse("2021-04-16T18:00:00.631Z").minusMillis((TimeUnit.HOURS.toMillis(8)));
        this.service.message().getHistoryAsUri(now).block();
    }

    @Test
    void testHistoryMsgGetAsLocalFile() {
//        Path path = FileSystems.getDefault().getPath("path");
//        assertDoesNotThrow(() -> this.service.message().getHistoryAsLocalFile(Instant.ofEpochSecond(1616407200), path, "history.gz").block(Duration.ofSeconds(3)));
    }

}
