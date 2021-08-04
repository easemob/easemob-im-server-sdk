package com.easemob.im.server.api.historymsg;

import com.easemob.im.server.api.AbstractIT;
import org.junit.jupiter.api.Disabled;
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

    // TODO: test failed and need to look into this
    @Disabled
    void testHistoryMsgGetAsUri() {
        this.service.message().getHistoryAsUri(
                Instant.now().minusMillis(TimeUnit.HOURS.toMillis(8))).block();
    }

    @Disabled
    void testHistoryMsgGetAsLocalFile() {
        Path path = FileSystems.getDefault().getPath("path");
        assertDoesNotThrow(() -> this.service.message().getHistoryAsLocalFile(Instant.ofEpochSecond(1616407200),
                path, "history.gz").block(Duration.ofSeconds(10)));
    }

}
