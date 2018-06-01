package com.watson.core.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

public class ThreadLevelFilter extends TurboFilter {
    private static ThreadLocal<Level> threadLocal = new ThreadLocal<>();

    public static void setLevel(Level level) {
        threadLocal.set(level);
    }

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        Level threadLevel = threadLocal.get();
        if (threadLevel != null && level.levelInt < threadLevel.levelInt)
            return FilterReply.DENY;
        return FilterReply.NEUTRAL;
    }
}
