package com.epam.rd.autocode.observer.git;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    List<WebHook> observers = new ArrayList<>();
    List<Event> events = new ArrayList<>();

    @Override
    public void addWebHook(WebHook webHook) {
        observers.add(webHook);
    }

    @Override
    public Commit commit(String branch, String author, String[] changes) {
        Commit commit = new Commit(author, changes);
        for (WebHook observer : observers) {
            if (observer.type() == Event.Type.COMMIT && observer.branch().compareTo(branch) == 0) {
                Event event = new Event(Event.Type.COMMIT, branch, List.of(commit));
                observer.onEvent(event);
                events.add(event);
            }
        }
        return commit;
    }

    @Override
    public void merge(String sourceBranch, String targetBranch) {
        List<Commit> commits = new ArrayList<>();
        WebHook source = null;
        WebHook target = null;
        for (WebHook webHook : observers) {
            if (webHook.branch().equals(sourceBranch) && webHook.type() == Event.Type.COMMIT) {
                source = webHook;
            } else if (webHook.branch().equals(targetBranch) && webHook.type() == Event.Type.MERGE) {
                target = webHook;
            }
        }
        if (source != null && target != null) {
            for (Event event : source.caughtEvents())
                commits.addAll(event.commits());
            if (commits.size() != 0)
                target.onEvent(new Event(Event.Type.MERGE, targetBranch, commits));
            observers.set(observers.indexOf(source), new WebHookImpl(source.branch(), source.type()));
        }
    }
}
