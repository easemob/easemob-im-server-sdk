package com.easemob.im.server.api.token.allocate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DynamicTokenGenerator {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String generateToken(String clientId, String appkey, String userId, String clientSecret, Long ttl)
            throws JsonProcessingException, NoSuchAlgorithmException {
        long curTime = Instant.now().getEpochSecond();
        String signature = sha1(clientId + appkey + userId + curTime + ttl + clientSecret);
        Map<String, Object> map = new HashMap<>();
        map.put("signature", signature);
        map.put("appkey", appkey);
        map.put("userId", userId);
        map.put("curTime", curTime);
        map.put("ttl", ttl);
        String json = objectMapper.writeValueAsString(map);
        return Base64.getEncoder().encodeToString(("dt-" + json).getBytes());
    }

    private static String sha1(String input) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] result = md.digest(input.getBytes());
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
