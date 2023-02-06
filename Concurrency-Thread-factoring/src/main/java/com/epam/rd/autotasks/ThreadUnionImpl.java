package com.epam.rd.autotasks;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadUnionImpl implements ThreadUnion {
    private final String name;
    private final CopyOnWriteArrayList<Thread> threads = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<FinishedThreadResult> threadResults = new CopyOnWriteArrayList<>();
    private boolean isShutdown = false;

    public ThreadUnionImpl(String name) {
        this.name = name;
    }

    @Override
    public int totalSize() {
        return threads.size();
    }

    @Override
    public int activeSize() {
        int activeThreadsCount = 0;
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                activeThreadsCount++;
            }
        }
        return activeThreadsCount;
    }

    @Override
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        isShutdown = true;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public void awaitTermination() {
        while (activeSize() != 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return activeSize() == 0 && isShutdown();
    }

    @Override
    public List<FinishedThreadResult> results() {
        return threadResults;
    }

    @Override
    public synchronized Thread newThread(Runnable r) {
        if (isShutdown) {
            throw new IllegalStateException();
        }

        Thread.UncaughtExceptionHandler h =
                (thread, throwable) -> threadResults.add( new FinishedThreadResult(thread.getName(), throwable) );

        Thread thread = new Thread(r) {
            public void run() {
                super.run();
                threadResults.add(new FinishedThreadResult(this.getName()));

            }
        };

        thread.setUncaughtExceptionHandler(h);
        thread.setName(name + "-worker-" + threads.size());
        threads.add(thread);
        return thread;
    }
}