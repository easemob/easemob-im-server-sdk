package com.easemob.im.server.api.sms;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SmsIT extends AbstractIT {
    public SmsIT() {
        super();
    }

    @Test
    public void testSmsSend() {
        Set<String> mobiles = new HashSet<>();
        mobiles.add("phoneNo");

        Map<String, String> tmap = new HashMap<>();
        tmap.put("p1", "p1");
        assertThrows(EMInternalServerErrorException.class, () -> this.service.sms().send(mobiles, "136", tmap, "888", "custom").block(Duration.ofSeconds(3)));
    }

}