package com.easemob.im.server.api.moderation;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModerationIT extends AbstractIT {

    ModerationIT() {
        super();
    }

    @Test
    public void testExportMessageRecord() {
        assertThrows(EMException.class, () -> this.service.moderation().export(1646602048000L, 1646666948000L, null, null, null, null).block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testExportDetailsList() {
        assertThrows(EMException.class,
                () -> this.service.moderation().get(1, 10, "287c0730-9e97-11ec-ba62-139a925bb42e").block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testRecordFileDownload() {
        assertThrows(EMException.class,
                () -> this.service.moderation().download("287c0730-9e97-11ec-ba62-139a925bb42e").block(Utilities.IT_TIMEOUT));
    }

}
