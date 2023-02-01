package com.epam.rd.autotasks;

public class CarouselRun {
    private int[] carousel;
    private int index;
    private int actionLimit = -1;
    private int actionCount;

    public CarouselRun(int[] carousel){
        this.carousel = carousel;
    }
    public CarouselRun(int[] carousel, int actionLimit){
        this.carousel = carousel;
        this.actionLimit = actionLimit;
    }

    public int next() {
        if(isFinished()) {
            return -1;
        } else {
            if(index == carousel.length){
                index = 0;
            }
            while (carousel[index] == 0){
                if(index == carousel.length-1){
                    index = 0;
                } else {
                    index++;
                }
            }
            int temp = carousel[index++]--;
            actionCount++;
            if(actionCount == actionLimit){
                carousel = new int[]{0};
            }
            return temp;
        }
    }

    public boolean isFinished() {
        for(int element : carousel) {
            if(element > 0) {
                return false;
            }
        }
        return true;
    }
}

