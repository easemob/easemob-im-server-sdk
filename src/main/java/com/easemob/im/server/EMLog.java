package com.easemob.im.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class EMLog {
    public enum Level {
        OFF,
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE,
        ALL
    }

    public static void setLogLevel(Level level) {
        org.apache.logging.log4j.Level log4jLevel;
        switch (level) {
            case OFF:
                log4jLevel = org.apache.logging.log4j.Level.OFF;
                break;
            case FATAL:
                log4jLevel = org.apache.logging.log4j.Level.FATAL;
                break;
            case ERROR:
                log4jLevel = org.apache.logging.log4j.Level.ERROR;
                break;
            case WARN:
                log4jLevel = org.apache.logging.log4j.Level.WARN;
                break;
            case INFO:
                log4jLevel = org.apache.logging.log4j.Level.INFO;
                break;
            case DEBUG:
                log4jLevel = org.apache.logging.log4j.Level.DEBUG;
                break;
            case TRACE:
                log4jLevel = org.apache.logging.log4j.Level.TRACE;
                break;
            case ALL:
                log4jLevel = org.apache.logging.log4j.Level.ALL;
                break;
            default:
                log4jLevel = org.apache.logging.log4j.Level.INFO;
        }

        Configurator.setAllLevels(LogManager.getRootLogger().getName(), log4jLevel);
    }
}
