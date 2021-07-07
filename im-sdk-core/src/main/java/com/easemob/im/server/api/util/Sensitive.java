package com.easemob.im.server.api.util;

public class Sensitive {
    // avoid instantiate
    private Sensitive() {
    }

    public static String mask(String text) {
        return text.replaceAll(".", "*");
    }
}
