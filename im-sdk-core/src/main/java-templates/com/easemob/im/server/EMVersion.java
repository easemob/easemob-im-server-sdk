package com.easemob.im.server;

public class EMVersion {
    public static String version = "${project.version}";

    public static String getVersion() {
        return EMVersion.version;
    }
}
