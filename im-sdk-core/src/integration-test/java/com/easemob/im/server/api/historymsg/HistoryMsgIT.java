package com.easemob.im.server.api.historymsg;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Disabled;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HistoryMsgIT extends AbstractIT {

    HistoryMsgIT() {
        super();
    }

    // TODO: disable them for now since msg history can be empty
    @Disabled
    void testHistoryMsgGetAsUri() {
        // minus 2 hours since msg history has an at least one hour delay
        this.service.message().getHistoryAsUri(Instant.now().minus(Duration.ofHours(2)))
                .block(Utilities.IT_TIMEOUT);
    }

    @Disabled
    void testHistoryMsgGetAsLocalFile() {
        Path path = FileSystems.getDefault().getPath("path");
        assertDoesNotThrow(() -> this.service.message()
                .getHistoryAsLocalFile(Instant.now().minus(Duration.ofHours(2)),
                        path, "history.gz").block(Utilities.IT_TIMEOUT));
    }

}
