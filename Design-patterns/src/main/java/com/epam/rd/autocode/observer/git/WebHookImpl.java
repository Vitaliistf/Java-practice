package com.epam.rd.autocode.observer.git;

import java.util.ArrayList;
import java.util.List;

public class WebHookImpl implements WebHook{
    private final String branch;
    private final Event.Type type;
    private final List<Event> caughtEvents;

    public WebHookImpl(String branch, Event.Type type) {
        this.branch = branch;
        this.type = type;
        this.caughtEvents = new ArrayList<>();
    }

    @Override
    public String branch() {
        return branch;
    }

    @Override
    public Event.Type type() {
        return type;
    }

    @Override
    public List<Event> caughtEvents() {
        return caughtEvents;
    }

    @Override
    public void onEvent(Event event) {
        caughtEvents.add(event);
    }
}
