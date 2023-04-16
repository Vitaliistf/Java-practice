package com.epam.rd.autocode.floodfill;

import java.util.ArrayList;
import java.util.List;

public class FloodLoggerImpl implements FloodLogger {

    List<String> log = new ArrayList<>();

    @Override
    public void log(final String floodState) {
        log.add(floodState);
    }
}
