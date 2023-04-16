package com.epam.rd.autotasks.sprintplanning.tickets;

public class Bug extends Ticket {

    UserStory userStory;

    public static Bug createBug(int id, String name, int estimate, UserStory userStory) {
        if(userStory.isCompleted()) {
            return new Bug(id, name, estimate, userStory);
        } else {
            return null;
        }
    }

    private Bug(int id, String name, int estimate, UserStory userStory) {
        super(id, name, estimate);
        this.userStory = userStory;
    }

    @Override
    public String toString() {
        return "[Bug " + getId() + "] " + userStory.getName() + ": " + getName();
    }
}
