package com.epam.rd.autotasks;

public class TaskCarousel {

    private Task[] tasks;
    private int index;

    public TaskCarousel(int capacity) {
        tasks = new Task[capacity];
        index = 0;
    }

    public boolean addTask(Task task) {
        if(task != null && !isFull() && !task.isFinished()){
            for (int i = 0; i < tasks.length; i++){
                if(tasks[i] == null){
                    tasks[i] = task;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean execute() {
        while (!isEmpty()) {
            if(index == tasks.length){
                index = 0;
            }

            if (tasks[index] != null) {
                tasks[index].execute();
                if (tasks[index].isFinished()) {
                    tasks[index] = null;
                }
                index++;
                return true;
            } else {
                index++;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (Task element : tasks){
            if(element == null){
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (Task element : tasks){
            if(element != null){
                return false;
            }
        }
        return true;
    }

}
