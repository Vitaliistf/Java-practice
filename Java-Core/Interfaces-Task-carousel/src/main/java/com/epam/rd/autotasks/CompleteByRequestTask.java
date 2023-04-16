package com.epam.rd.autotasks;

public class CompleteByRequestTask implements Task {

    private boolean completed;
    private boolean executed;

    @Override
    public void execute() {
        if(completed) {
            executed = true;
        }
    }

    @Override
    public boolean isFinished() {
        return executed;
    }

    public void complete() {
        completed = true;
    }
}
