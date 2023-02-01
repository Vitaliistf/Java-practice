package com.epam.rd.autocode.floodfill;

import java.util.ArrayList;
import java.util.List;

public class RecursionCountingFloodLoggerImpl extends FloodLoggerImpl {

    List<String> log = new ArrayList<>();
    List<Long> methodChainCounts = new ArrayList<>();

    @Override
    public void log(final String floodState) {
        log.add(floodState);

        final Long methodChainCount = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames
                        .dropWhile(frame -> !isFloodMethod(frame))
//                        .peek(frame -> System.out.println(frame.getMethodName() + " " + frame.getMethodType().toString()))
                        .takeWhile(frame -> isFloodMethod(frame))
                        .count());
        methodChainCounts.add(methodChainCount);

    }

    private boolean isFloodMethod(final StackWalker.StackFrame frame) {
        return frame.getMethodName().equals("flood")
                && frame.getMethodType().parameterList().equals(List.of(String.class, FloodLogger.class));
    }
}
