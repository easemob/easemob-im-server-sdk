package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static com.easemob.im.server.utils.RandomMaker.makeRandomUserName;

public class RenewAppTokenIT {

   private static final String BASE_URI = System.getenv("IM_BASE_URI");
   private static final String APP_KEY = System.getenv("IM_APPKEY");
   private static final String APP_ID = System.getenv("IM_APP_ID");
   private static final String APP_CERT = System.getenv("IM_APP_CERT");

   private static final Logger log = LoggerFactory.getLogger(RenewAppTokenIT.class);

   private final EMService service;

   private void sleep(int seconds) {
      try {
         Thread.sleep(seconds * 1000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public RenewAppTokenIT() {
      EMProperties agoraGateway = EMProperties.agoraRealmBuilder()
              .setExpireSeconds(20)
              .setBaseUri(BASE_URI)
              .setAppkey(APP_KEY)
              .setAppId(APP_ID)
              .setAppCert(APP_CERT)
              .setHttpConnectionPoolSize(10)
              .setServerTimezone("+8")
              .build();
      this.service = new EMService(agoraGateway);
   }

   @Test
   public void getUser() {
      String randomUserName = makeRandomUserName();
      assertDoesNotThrow(() -> this.service.user().create(randomUserName, randomUserName)
              .block(Duration.ofSeconds(30)));
      int successCount = 0;
      try {
         for (int i = 0; i < 30; i ++) {
            sleep(1);
            EMUser user = service.user().get(randomUserName).block(Duration.ofSeconds(30));
            successCount ++;
         }
      } catch (Throwable th) {
         log.error("failed to GET user", th);
      } finally {
         log.info("successCount = {}", successCount);
      }
      Assertions.assertEquals(30, successCount);
   }
}
