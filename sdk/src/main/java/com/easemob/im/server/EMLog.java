package com.easemob.im.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.Level.ALL;
import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.FATAL;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.OFF;
import static org.apache.logging.log4j.Level.TRACE;
import static org.apache.logging.log4j.Level.WARN;

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

    public static Map<Level, org.apache.logging.log4j.Level> toLog4j = new HashMap<>();

    static {
        toLog4j.put(Level.OFF, OFF);
        toLog4j.put(Level.FATAL, FATAL);
        toLog4j.put(Level.ERROR, ERROR);
        toLog4j.put(Level.WARN, WARN);
        toLog4j.put(Level.INFO, INFO);
        toLog4j.put(Level.DEBUG, DEBUG);
        toLog4j.put(Level.TRACE, TRACE);
        toLog4j.put(Level.ALL, ALL);
    }

    public static Map<org.apache.logging.log4j.Level, Level> fromLog4j = new HashMap<>();

    static {
        fromLog4j.put(OFF, Level.OFF);
        fromLog4j.put(FATAL, Level.FATAL);
        fromLog4j.put(ERROR, Level.ERROR);
        fromLog4j.put(WARN, Level.WARN);
        fromLog4j.put(INFO, Level.INFO);
        fromLog4j.put(DEBUG, Level.DEBUG);
        fromLog4j.put(TRACE, Level.TRACE);
        fromLog4j.put(ALL, Level.ALL);
    }


    public static void setLogLevel(Level level) {
        Configurator.setRootLevel(toLog4j.get(level));
    }

    public Level getLogLevel()
    {
        return fromLog4j.get(LogManager.getRootLogger().getLevel());
    }

    public static boolean isDebugEnabled() {
        return LogManager.getRootLogger().isDebugEnabled();
    }

    public static boolean isTraceEnabled() {
        return LogManager.getRootLogger().isTraceEnabled();
    }

}
