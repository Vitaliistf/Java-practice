package com.epam.rd.autotasks;

public class CountDownTask implements Task{
    private int value;

    public CountDownTask(int value) {
        if(value < 0){
            this.value = 0;
        } else {
            this.value = value;
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public void execute() {
        if(value > 0)
            value--;
    }

    @Override
    public boolean isFinished() {
        return value == 0;
    }
}
