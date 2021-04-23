package com.easemob.im.server.api.sms.send;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SendSmsTest  extends AbstractApiTest {

    SendSms sendSms = new SendSms(this.context);

    public SendSmsTest() {
        this.server.addHandler("POST /easemob/demo/sms/send", this::handleSendSms);
    }

    @Test
    public void testSendSms() {
        Set<String> mobiles = new HashSet<>();
        mobiles.add("phoneNo");

        Map<String, String> tmap = new HashMap<>();
        tmap.put("p1", "p1");
        assertDoesNotThrow(() -> this.sendSms.send(mobiles, "136", tmap, "888", "custom").block(Duration.ofSeconds(3)));
    }

    public JsonNode handleSendSms(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("count", 1);
        jsonRsp.put("msg", "短信发送成功！");
        return jsonRsp;
    }
}