package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

import java.util.Arrays;

public class Sprint {
    private final int capacity;
    private final int ticketsLimit;
    private Ticket[] tickets;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        tickets = new Ticket[0];
    }

    public boolean addUserStory(UserStory userStory) {

        if(userStory == null || userStory.isCompleted() || tickets.length == ticketsLimit)
            return false;
        if(getTotalEstimate() + userStory.getEstimate() > capacity)
            return false;

        //Comparing id of uncompleted dependencies and accepted user stories
        for(UserStory story : userStory.getDependencies()) {
            if(!story.isCompleted()){
                boolean ticketIsAccepted = false;
                for(Ticket ticket : this.getTickets()) {
                    if(ticket != null && story.getId() == ticket.getId()) {
                        ticketIsAccepted = true;
                        break;
                    }
                }
                if(!ticketIsAccepted) {
                    return false;
                }
            }
        }

        tickets = Arrays.copyOf(tickets, tickets.length+1);
        tickets[tickets.length-1] = userStory;
        return true;
    }

    public boolean addBug(Bug bugReport) {
        if(bugReport == null) return false;
        if(getTotalEstimate() + bugReport.getEstimate() > capacity || tickets.length == ticketsLimit) return false;
        if(!bugReport.isCompleted()){
            tickets = Arrays.copyOf(tickets, tickets.length+1);
            tickets[tickets.length-1] = bugReport;
            return true;
        }
        return false;
    }

    public Ticket[] getTickets() {
        return Arrays.copyOf(tickets, tickets.length);
    }

    public int getTotalEstimate() {
        int result = 0;
        for (Ticket ticket : tickets){
            if(ticket != null)
                result += ticket.getEstimate();
        }
        return result;
    }
}
