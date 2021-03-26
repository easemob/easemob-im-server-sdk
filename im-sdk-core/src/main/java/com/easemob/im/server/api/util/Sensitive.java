package com.easemob.im.server.api.util;

public class Sensitive {
    public static String mask(String text) {
        return text.replaceAll(".", "*");
    }

    // avoid instantiate
    private Sensitive() {}
}
