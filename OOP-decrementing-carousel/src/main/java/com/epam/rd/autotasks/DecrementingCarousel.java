package com.epam.rd.autotasks;

public class DecrementingCarousel {
    protected int[] array;
    protected boolean running;

    public DecrementingCarousel(int capacity) {
        array = new int[capacity];
    }

    public boolean addElement(int element){
        if (element <= 0 || running){
            return false;
        }
        for(int i = 0; i < array.length; i++){
            if(array[i] == 0){
                array[i] = element;
                return true;
            }
        }
        return false;
    }

    public CarouselRun run(){
        if(running){
            return null;
        }
        running = true;
        return new CarouselRun(this.array);
    }


}
